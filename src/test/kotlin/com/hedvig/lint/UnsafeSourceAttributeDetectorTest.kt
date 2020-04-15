package com.hedvig.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.jupiter.api.Test

class UnsafeSourceAttributeDetectorTest {
    @Test
    fun `given a layout XML file, when encountering an ImageView with src, should report that ImageView`() {
        lint()
            .files(
                xml(
                    "res/layout/activity_test.xml",
                    """
<ImageView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:src="@drawable/example" />
                    """.trimIndent()
                )
            )
            .issues(UnsafeSourceAttributeDetector.ISSUE)
            .run()
            .expect(
                """
                res/layout/activity_test.xml:1: Warning: Unsafe android:src-attribute [UnsafeSourceAttribute]
                <ImageView
                ^
                0 errors, 1 warnings
            """.trimIndent()
            )
    }

    @Test
    fun `given a layout XML file, when encountering an ImageView with src and applying fix, should apply that fix`() {
        lint()
            .files(
                xml(
                    "res/layout/activity_test.xml",
                    """
<?xml version="1.0" encoding="utf-8"?>
<ImageView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:src="@drawable/example" />
                    """.trimIndent()
                )
            )
            .issues(UnsafeSourceAttributeDetector.ISSUE)
            .run()
            .expectFixDiffs(
                """
                Fix for res/layout/activity_test.xml line 2: replace with `app:srcCompat`:
                @@ -3 +3
                +     xmlns:app="http://schemas.android.com/apk/res-auto"
                @@ -5 +6
                -     android:src="@drawable/example" />
                @@ -6 +6
                +     app:srcCompat="@drawable/example" />""".trimIndent()
            )
    }

    @Test
    fun `given a layout XML file, when encountering an ImageView with srcCompat, should not report that ImageView`() {
        lint()
            .files(
                xml(
                    "res/layout/activity_test.xml",
                    """
<ImageView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:srcCompat="@drawable/example" />
                    """.trimIndent()
                )
            )
            .issues(UnsafeSourceAttributeDetector.ISSUE)
            .run()
            .expectClean()
    }
}
