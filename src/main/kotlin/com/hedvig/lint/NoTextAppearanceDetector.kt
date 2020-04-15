package com.hedvig.lint

import com.android.SdkConstants
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Element

class NoTextAppearanceDetector : LayoutDetector() {
    override fun getApplicableElements() = listOf("TextView")

    override fun visitElement(context: XmlContext, element: Element) {
        if (!element.hasAttribute("android:textAppearance")) {
            val fix = fix()
                .set()
                .todo(SdkConstants.ANDROID_URI, "textAppearance")
                .build()
            context.report(ISSUE, element, context.getLocation(element), "Missing `android:textAppearance`", fix)
        }
    }

    companion object {
        val ISSUE = Issue.create(
            "NoTextAppearance",
            "`<TextView />`s are required to have the attribute `android:textAppearance`",
            """
                In order to be correctly themed, a <TextView /> should always declare a
                `android:textAppearance`-attribute. This attribute should as a rule be a Theme Attribute.
            """.trimMargin(),
            Category.CORRECTNESS,
            10,
            Severity.WARNING,
            Implementation(NoTextAppearanceDetector::class.java, Scope.RESOURCE_FILE_SCOPE)
        )
    }
}
