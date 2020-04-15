package com.hedvig.lint

import com.android.tools.lint.client.api.IssueRegistry

class HedvigIssueRegistry : IssueRegistry() {
    override val issues = listOf(
        NoHardcodedDimensDetector.ISSUE,
        NoRelativeLayoutDetector.ISSUE,
        NoTextAppearanceDetector.ISSUE,
        UnsafeSourceAttributeDetector.ISSUE
    )
}
