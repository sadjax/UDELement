/*
 * Copyright (c) 2020 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.app.core.ui.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import im.vector.app.R
import im.vector.app.core.extensions.setTextOrHide
import im.vector.app.databinding.ItemVerificationActionBinding
import im.vector.app.features.themes.ThemeUtils

class BottomSheetActionButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val views : ItemVerificationActionBinding

    var title: String? = null
        set(value) {
            field = value
            views.itemVerificationActionTitle.setTextOrHide(value)
        }

    var subTitle: String? = null
        set(value) {
            field = value
            views.itemVerificationActionSubTitle.setTextOrHide(value)
        }

    var forceStartPadding: Boolean? = null
        set(value) {
            field = value
            if (leftIcon == null) {
                if (forceStartPadding == true) {
                    views.itemVerificationLeftIcon.isInvisible = true
                } else {
                    views.itemVerificationLeftIcon.isGone = true
                }
            }
        }

    var leftIcon: Drawable? = null
        set(value) {
            field = value
            if (value == null) {
                if (forceStartPadding == true) {
                    views.itemVerificationLeftIcon.isInvisible = true
                } else {
                    views.itemVerificationLeftIcon.isGone = true
                }
                views.itemVerificationLeftIcon.setImageDrawable(null)
            } else {
                views.itemVerificationLeftIcon.isVisible = true
                views.itemVerificationLeftIcon.setImageDrawable(value)
            }
        }

    var rightIcon: Drawable? = null
        set(value) {
            field = value
            views.itemVerificationActionIcon.setImageDrawable(value)
        }

    var tint: Int? = null
        set(value) {
            field = value
            views.itemVerificationLeftIcon.imageTintList = value?.let { ColorStateList.valueOf(value) }
        }

    var titleTextColor: Int? = null
        set(value) {
            field = value
            value?.let { views.itemVerificationActionTitle.setTextColor(it) }
        }

    init {
        inflate(context, R.layout.item_verification_action, this)
        views = ItemVerificationActionBinding.bind(this)

        context.withStyledAttributes(attrs, R.styleable.BottomSheetActionButton) {
            title = getString(R.styleable.BottomSheetActionButton_actionTitle) ?: ""
            subTitle = getString(R.styleable.BottomSheetActionButton_actionDescription) ?: ""
            forceStartPadding = getBoolean(R.styleable.BottomSheetActionButton_forceStartPadding, false)
            leftIcon = getDrawable(R.styleable.BottomSheetActionButton_leftIcon)

            rightIcon = getDrawable(R.styleable.BottomSheetActionButton_rightIcon)

            tint = getColor(R.styleable.BottomSheetActionButton_tint, ThemeUtils.getColor(context, android.R.attr.textColor))
            titleTextColor = getColor(R.styleable.BottomSheetActionButton_titleTextColor, ContextCompat.getColor(context, R.color.riotx_accent))
        }
    }
}
