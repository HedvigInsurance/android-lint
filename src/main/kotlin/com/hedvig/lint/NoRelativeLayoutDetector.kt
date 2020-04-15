package com.hedvig.lint

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Element

class NoRelativeLayoutDetector : LayoutDetector() {
    override fun getApplicableElements() = listOf("RelativeLayout")

    override fun visitElement(context: XmlContext, element: Element) {
        context.report(ISSUE, element, context.getElementLocation(element), "No `<RelativeLayout />`")
    }

    companion object {
        val ISSUE = Issue.create(
            "NoRelativeLayout",
            "`<RelativeLayout />` should not be used",
            "`<RelativeLayout />` is obsolete. Any use-cases that requires it should be replaced by `<ConstraintLayout />`",
            Category.PERFORMANCE,
            10,
            Severity.WARNING,
            Implementation(NoRelativeLayoutDetector::class.java, Scope.RESOURCE_FILE_SCOPE)
        )
    }
}
