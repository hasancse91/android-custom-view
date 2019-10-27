# Android Custom View

Sometimes we need to use same type of view in multiple screen of Android App. We can create a custom view to make our code reusable.
Below screenshot you will see sunrise and sunset time using custom view.

<img src="https://raw.githubusercontent.com/hasancse91/android-custom-view/master/data/screenshot.jpg" width="250" height="444" />

You can easily design this UI in traditional way. All `TextView`, `ImageView` in the `xml` file of `Activity`. How many UI component do you need to design this UI? You need to use four components for each item. So to design above UI there will be 8 components (TextView, ImageView) to design sunrise and sunset view. What if you need 5 view like sunrise/sunset view? You have to use 5*4=20 components! And the `xml` code will be very messy.

You can avoid this code redundancy, you can put this view in a seperate xml (`layout/custom_view.xml`) file like below:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:ignore="ContentDescription"
        tools:src="@drawable/location" />

    <TextView
        android:id="@+id/titleTextView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginTop="8dp"
        android:textSize="16sp"
        android:textStyle="bold"
        app:layout_constraintStart_toEndOf="@+id/imageView"
        app:layout_constraintTop_toTopOf="@+id/imageView"
        tools:text="This is title. It should be in one line" />

    <TextView
        android:id="@+id/subtitleTextView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="4dp"
        app:layout_constraintStart_toStartOf="@+id/titleTextView"
        app:layout_constraintTop_toBottomOf="@+id/titleTextView"
        tools:text="This is subtitle" />

    <View
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:layout_marginTop="8dp"
        android:background="@color/color_divider"
        app:layout_constraintTop_toBottomOf="@id/imageView" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
And you can use `include` tag in activity layout. But if we create a custom view then we don't need to use `<include>` tag. We can treat our view like a single view (e.g. TextView).

So that our `xml` code of `Activity` layout will be like this:
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/custom_view_label"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <com.hellohasan.androidcustomview.CustomView
        android:id="@+id/sunrise_custom_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="24dp"
        app:layout_constraintTop_toBottomOf="@+id/textView"
        app:setImageDrawable="@drawable/sun_rise"
        app:setTitle="Sun rise time - Dhaka, Bangladesh"
        tools:setSubTitle="5:25 AM" />

    <com.hellohasan.androidcustomview.CustomView
        android:id="@+id/sunset_custom_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toBottomOf="@+id/sunrise_custom_view"
        app:setImageDrawable="@drawable/sunset"
        app:setTitle="Sunset time - Dhaka, Bangladesh"
        tools:setSubTitle="6:12 PM" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
Here, `com.hellohasan.androidcustomview.CustomView` is our custom view. And we set our custom attributes (image drawable, title and subtitle) from xml.
To acheive this functionality we have to create file **`values/attrs`**.
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <declare-styleable name="CustomView">
        <attr name="setImageDrawable" format="reference" />
        <attr name="setTitle" format="string" />
        <attr name="setSubTitle" format="string" />
    </declare-styleable>

</resources>
```
Now we will create `CustomView.kt` for custom view.
```kotlin
import androidx.constraintlayout.widget.ConstraintLayout

import android.content.Context
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.Drawable
import android.widget.TextView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.Nullable
import kotlinx.android.synthetic.main.custom_view.view.*
import java.lang.Exception

class CustomView(context: Context, @Nullable attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var view : View = LayoutInflater.from(context).inflate(R.layout.custom_view, this, true)
    private var imageView: ImageView
    private var titleTextView: TextView
    private var subtitleTextView: TextView
    private var imageDrawable : Drawable?
    private var title: String?
    private var subtitle: String?

    init {
        imageView = view.imageView as ImageView
        titleTextView = view.titleTextView as TextView
        subtitleTextView = view.subtitleTextView as TextView

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0)

        try {
            imageDrawable = typedArray.getDrawable(R.styleable.CustomView_setImageDrawable)
            title = typedArray.getString(R.styleable.CustomView_setTitle)
            subtitle = typedArray.getString(R.styleable.CustomView_setSubTitle)

            imageView.setImageDrawable(imageDrawable)
            titleTextView.text = title
            subtitleTextView.text = subtitle
        }
        finally {
            typedArray.recycle()
        }

        /** Uncomment below line if all of your attribute fields are required.
         * Throw an exception if required attributes are not set. It will caused Run Time Exception.
         * In this sample project we assume that, no attributes are mandatory
         * *
         */
        /*
        if (imageDrawable == null)
            throw RuntimeException("No image drawable provided")
        if (title == null) {
            throw RuntimeException("No title provided")
        }
        if (subtitle == null) {
            throw RuntimeException("No subtitle provided")
        }*/
    }

    /**
     * Below getter-setter will work, if we need to access the attributes programmatically
     */

    fun setImageDrawable(drawable: Drawable?) {
        imageView.setImageDrawable(drawable)
    }
    fun getImageDrawable() : Drawable? {
        return imageDrawable
    }

    fun setTitle(text: String?) {
        titleTextView.text = text
    }
    fun getTitle() : String? {
        return title
    }

    fun setSubtitle(text: String?) {
        subtitleTextView.text = text
    }
    fun getSubtitle() : String? {
        return subtitle
    }
}
```
We can set our custom attributes from xml and also from Java/Kotlin code. Here is out `MainActivity.kt`. We already set image drawable and title from xml of `MainActivity`. Now we will set the subtitle programmatically from Kotlin class.
```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // image drawable and title are already set from xml.
        // I want to set subtitle programmatically. you can set title and image drawable from here
        sunrise_custom_view.setSubtitle("5:31 AM") // sunrise time set as subtitle
        sunset_custom_view.setSubtitle("5:01 PM") // subset time set as subtitle
    }
}
```
