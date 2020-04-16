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

class UnsafeSourceAttributeDetector : LayoutDetector() {
    override fun getApplicableElements() = listOf("ImageView")

    override fun visitElement(context: XmlContext, element: Element) {
        if (element.hasAttribute("android:src")) {
            val srcContent = element.getAttribute("android:src")

            val fix = fix()
                .name("replace with `app:srcCompat`")
                .composite(
                    fix()
                        .set(SdkConstants.AUTO_URI, "srcCompat", srcContent)
                        .build(),
                    fix()
                        .unset(SdkConstants.ANDROID_URI, "src")
                        .build()
                )

            context.report(ISSUE, element, context.getLocation(element), "Unsafe `android:src`-attribute", fix)
        }
    }

    companion object {
        val ISSUE = Issue.create(
            "UnsafeSourceAttribute",
            "`<ImageView />`s should use `app:srcCompat` for compatibility reasons",
            """
                In order to not crash when loading `VectorDrawable`s with the AppCompat-libraries,
                `<ImageView />`s should use `app:srcCompat` over `android:src`
            """.trimIndent(),
            Category.CORRECTNESS,
            10,
            Severity.WARNING,
            Implementation(UnsafeSourceAttributeDetector::class.java, Scope.RESOURCE_FILE_SCOPE)
        )
    }
}
