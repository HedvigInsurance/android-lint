package com.hedvig.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.jupiter.api.Test

class NoHardcodedDimensDetectorTest {
    @Test
    fun `given a layout XML file, when encountering a hardcoded dimension, should report that dimension`() {
        lint()
            .files(
                xml(
                    "res/layout/activity_test.xml",
                    """
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingTop="8dp" />""".trimIndent()
                )
            )
            .issues(NoHardcodedDimensDetector.ISSUE)
            .run()
            .expect(
                """
                res/layout/activity_test.xml:5: Warning: No hardcoded dimens [NoHardcodedDimens]
                    android:paddingTop="8dp" />
                                        ~~~
                0 errors, 1 warnings""".trimIndent()
            )
    }

    @Test
    fun `given a layout XML file, when encountering a dimens reference, should not report anything`() {
        lint()
            .files(
                xml(
                    "res/layout/activity_test.xml",
                    """
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimens/base_margin" />""".trimIndent()
                )
            ).issues(NoHardcodedDimensDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun `given a layout XML file, when encountering a dimens attribute, should not report anything`() {
        lint()
            .files(
                xml(
                    "res/layout/activity_test.xml",
                    """
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="?baseMargin" />""".trimIndent()
                )
            )
            .issues(NoHardcodedDimensDetector.ISSUE)
            .run()
            .expectClean()
    }
}
