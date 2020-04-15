package com.hedvig.lint

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.ResourceXmlDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Attr

class NoHardcodedDimensDetector : ResourceXmlDetector() {
    override fun getApplicableAttributes() = listOf(
        "layout_width",
        "layout_height",
        "width",
        "height",
        "layout_margin",
        "layout_marginTop",
        "layout_marginRight",
        "layout_marginEnd",
        "layout_marginBottom",
        "layout_marginLeft",
        "layout_marginStart",
        "padding",
        "paddingTop",
        "paddingRight",
        "paddingEnd",
        "paddingBottom",
        "paddingLeft",
        "paddingStart"
    )

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        // Allow match_parent/wrap_content/0dp (match_constraint) for layout_width/layout_height
        if (attribute.name == "android:layout_width" || attribute.name == "android:layout_height") {
            if (attribute.value == "match_parent" || attribute.value == "wrap_content" || attribute.value == "0dp") {
                return
            }
        }
        if (!attribute.value.startsWith("@") && !attribute.value.startsWith("?")) {
            context.report(ISSUE, attribute, context.getValueLocation(attribute), "No hardcoded dimens")
        }
    }

    companion object {
        val ISSUE = Issue.create(
            "NoHardcodedDimens",
            "Dimens should not be hardcoded in layout files",
            """
                Dimens should not be hardcoded in layout files, as this leads to less maintainable code.
                Prefer using a reference to a dimension: `@dimens/example` or a theme attribute: `?exampleAttr`.""".trimIndent(),
            Category.CORRECTNESS,
            10,
            Severity.WARNING,
            Implementation(NoHardcodedDimensDetector::class.java, Scope.RESOURCE_FILE_SCOPE)
        )
    }
}
