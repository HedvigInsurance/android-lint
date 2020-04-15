package com.hedvig.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.jupiter.api.Test

class NoTextAppearanceDetectorTest {
    @Test
    fun `given a layout XML file, when encountering TextView without textAppearance, should report that TextView`() {
        lint()
            .files(
                xml(
                    "res/layout/activity_test.xml",
                    """
<TextView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
                    """.trimIndent()
                )
            )
            .issues(NoTextAppearanceDetector.ISSUE)
            .run()
            .expect(
                """res/layout/activity_test.xml:1: Warning: Missing android:textAppearance [NoTextAppearance]
<TextView
^
0 errors, 1 warnings"""
            )
    }

    @Test
    fun `given a layout XML file, when encountering TextView without textAppearance and applying fix, should apply fix`() {
        lint()
            .files(
                xml(
                    "res/layout/activity_test.xml",
                    """
<TextView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
                    """.trimIndent()
                )
            )
            .issues(NoTextAppearanceDetector.ISSUE)
            .run()
            .expectFixDiffs(
                """
                Fix for res/layout/activity_test.xml line 1: Set textAppearance:
                @@ -4 +4
                -     android:layout_height="wrap_content" />
                @@ -5 +4
                +     android:layout_height="wrap_content"
                +     android:textAppearance="[TODO]|" />
            """.trimIndent()
            )
    }

    @Test
    fun `given a layout XML file, when encountering a TextView with textAppearance, should not report that TextView`() {
        lint()
            .files(
                xml(
                    "res/layout/activity_test.xml",
                    """
<TextView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textAppearance="?textAppearanceBody1"
/>
                    """.trimIndent()
                )
            )
            .issues(NoTextAppearanceDetector.ISSUE)
            .run()
            .expectClean()
    }
}
