package com.hedvig.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.jupiter.api.Test

class NoRelativeLayoutDetectorTest {
    @Test
    fun `given a layout XML file, when encountering a RelativeLayout, should report that RelativeLayout`() {
        lint()
            .files(
                xml(
                    "res/layout/activity_test.xml",
                    """
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
                    """.trimIndent()
                )
            )
            .issues(NoRelativeLayoutDetector.ISSUE)
            .run()
            .expect(
                """
                res/layout/activity_test.xml:1: Warning: No <RelativeLayout /> [NoRelativeLayout]
                <RelativeLayout
                 ~~~~~~~~~~~~~~
                0 errors, 1 warnings""".trimIndent()
            )
    }

    @Test
    fun `given a layout XML file, when not encountering a RelativeLayout, should not report anything`() {
        lint()
            .files(
                xml(
                    "res/layout/activity_test.xml",
                    """
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
                    """.trimIndent()
                )
            )
            .issues(NoRelativeLayoutDetector.ISSUE)
            .run()
            .expectClean()
    }
}
