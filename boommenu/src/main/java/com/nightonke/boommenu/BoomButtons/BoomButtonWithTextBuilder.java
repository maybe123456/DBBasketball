package com.nightonke.boommenu.BoomButtons;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;

/**
 * Created by Weiping Huang at 01:12 on 2017/4/7
 * For Personal Open Source
 * Contact me at 2584541288@qq.com or nightonke@outlook.com
 * For more projects: https://github.com/Nightonke
 */

@SuppressWarnings({"unchecked", "unused"})
public abstract class BoomButtonWithTextBuilder<T> extends BoomButtonBuilder<T> {

    /**
     * Set the inner_tv when boom-button is at normal-state.
     * <br><br>
     * <STRONG>Synchronicity:</STRONG> If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param normalText inner_tv
     * @return the builder
     */
    public T normalText(String normalText) {
        if (this.normalText == null || !this.normalText.equals(normalText)) {
            this.normalText = normalText;
            BoomButton button = button();
            if (button != null) {
                button.normalText = normalText;
                button.updateText();
            }
        }
        return (T) this;
    }

    /**
     * Set the inner_tv resource when boom-button is at normal-state.
     * <br><br>
     * <STRONG>Synchronicity:</STRONG> If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param normalTextRes inner_tv resource
     * @return the builder
     */
    public T normalTextRes(int normalTextRes) {
        if (this.normalTextRes != normalTextRes) {
            this.normalTextRes = normalTextRes;
            BoomButton button = button();
            if (button != null) {
                button.normalTextRes = normalTextRes;
                button.updateText();
            }
        }
        return (T) this;
    }

    /**
     * Set the inner_tv when boom-button is at highlighted-state.
     * <br><br>
     * <STRONG>Synchronicity:</STRONG> If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param highlightedText inner_tv
     * @return the builder
     */
    public T highlightedText(String highlightedText) {
        if (this.highlightedText == null || !this.highlightedText.equals(highlightedText)) {
            this.highlightedText = highlightedText;
            BoomButton button = button();
            if (button != null) {
                button.highlightedText = highlightedText;
                button.updateText();
            }
        }
        return (T) this;
    }

    /**
     * Set the inner_tv resource when boom-button is at highlighted-state.
     * <br><br>
     * <STRONG>Synchronicity:</STRONG> If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param highlightedTextRes inner_tv resource
     * @return the builder
     */
    public T highlightedTextRes(int highlightedTextRes) {
        if (this.highlightedTextRes != highlightedTextRes) {
            this.highlightedTextRes = highlightedTextRes;
            BoomButton button = button();
            if (button != null) {
                button.highlightedTextRes = highlightedTextRes;
                button.updateText();
            }
        }
        return (T) this;
    }

    /**
     * Set the inner_tv when boom-button is at unable-state.
     * <br><br>
     * <STRONG>Synchronicity:</STRONG> If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param unableText inner_tv
     * @return the builder
     */
    public T unableText(String unableText) {
        if (this.unableText == null || !this.unableText.equals(unableText)) {
            this.unableText = unableText;
            BoomButton button = button();
            if (button != null) {
                button.unableText = unableText;
                button.updateText();
            }
        }
        return (T) this;
    }

    /**
     * Set the inner_tv resource when boom-button is at unable-state.
     * <br><br>
     * <STRONG>Synchronicity:</STRONG> If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param unableTextRes inner_tv resource
     * @return the builder
     */
    public T unableTextRes(int unableTextRes) {
        if (this.unableTextRes != unableTextRes) {
            this.unableTextRes = unableTextRes;
            BoomButton button = button();
            if (button != null) {
                button.unableTextRes = unableTextRes;
                button.updateText();
            }
        }
        return (T) this;
    }

    /**
     * Set the color of inner_tv when boom-button is at normal-state.
     * <br><br>
     * <STRONG>Synchronicity:</STRONG> If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param normalTextColor color of inner_tv
     * @return the builder
     */
    public T normalTextColor(int normalTextColor) {
        if (this.normalTextColor != normalTextColor) {
            this.normalTextColor = normalTextColor;
            BoomButton button = button();
            if (button != null) {
                button.normalTextColor = normalTextColor;
                button.updateText();
            }
        }
        return (T) this;
    }

    /**
     * Set the color of inner_tv when boom-button is at normal-state.
     * <br><br>
     * <STRONG>Synchronicity:</STRONG> If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param normalTextColorRes color resource of inner_tv
     * @return the builder
     */
    public T normalTextColorRes(int normalTextColorRes) {
        if (this.normalTextColorRes != normalTextColorRes) {
            this.normalTextColorRes = normalTextColorRes;
            BoomButton button = button();
            if (button != null) {
                button.normalTextColorRes = normalTextColorRes;
                button.updateText();
            }
        }
        return (T) this;
    }

    /**
     * Set the color of inner_tv when boom-button is at highlighted-state.
     * <br><br>
     * <STRONG>Synchronicity:</STRONG> If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param highlightedTextColor color of inner_tv
     * @return the builder
     */
    public T highlightedTextColor(int highlightedTextColor) {
        if (this.highlightedTextColor != highlightedTextColor) {
            this.highlightedTextColor = highlightedTextColor;
            BoomButton button = button();
            if (button != null) {
                button.highlightedTextColor = highlightedTextColor;
                button.updateText();
            }
        }
        return (T) this;
    }

    /**
     * Set the color of inner_tv when boom-button is at highlighted-state.
     * <br><br>
     * <STRONG>Synchronicity:</STRONG> If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param highlightedTextColorRes color resource of inner_tv
     * @return the builder
     */
    public T highlightedTextColorRes(int highlightedTextColorRes) {
        if (this.highlightedTextColorRes != highlightedTextColorRes) {
            this.highlightedTextColorRes = highlightedTextColorRes;
            BoomButton button = button();
            if (button != null) {
                button.highlightedTextColorRes = highlightedTextColorRes;
                button.updateText();
            }
        }
        return (T) this;
    }

    /**
     * Set the color of inner_tv when boom-button is at unable-state.
     * <br><br>
     * <STRONG>Synchronicity:</STRONG> If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param unableTextColor color of inner_tv
     * @return the builder
     */
    public T unableTextColor(int unableTextColor) {
        if (this.unableTextColor != unableTextColor) {
            this.unableTextColor = unableTextColor;
            BoomButton button = button();
            if (button != null) {
                button.unableTextColor = unableTextColor;
                button.updateText();
            }
        }
        return (T) this;
    }

    /**
     * Set the color of inner_tv when boom-button is at unable-state.
     * <br><br>
     * <STRONG>Synchronicity:</STRONG> If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param unableTextColorRes color resource of inner_tv
     * @return the builder
     */
    public T unableTextColorRes(int unableTextColorRes) {
        if (this.unableTextColorRes != unableTextColorRes) {
            this.unableTextColorRes = unableTextColorRes;
            BoomButton button = button();
            if (button != null) {
                button.unableTextColorRes = unableTextColorRes;
                button.updateText();
            }
        }
        return (T) this;
    }

    /**
     * Set the rect of inner_tv.
     * By this method, you can set the position and size of the inner_tv-view in boom-button.
     * For example, builder.textRect(new Rect(0, 50, 100, 100)) will make the
     * inner_tv-view's size to be 100 * 50 and margin-top to be 50 pixel.
     * <br><br>
     * <STRONG>Synchronicity:</STRONG> If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param textRect the inner_tv rect, in pixel.
     * @return the builder
     */
    public T textRect(Rect textRect) {
        if (this.textRect != textRect) {
            this.textRect = textRect;
            BoomButton button = button();
            if (button != null) {
                button.textRect = textRect;
                button.updateTextRect();
            }
        }
        return (T) this;
    }

    /**
     * Set the padding of inner_tv.
     * By this method, you can control the padding in the inner_tv-view.
     * For instance, builder.textPadding(new Rect(10, 10, 10, 10)) will make the
     * inner_tv-view content 10-pixel padding to itself.
     * <br><br>
     * <STRONG>Synchronicity:</STRONG> If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param textPadding the inner_tv padding
     * @return the builder
     */
    public T textPadding(Rect textPadding) {
        if (this.textPadding != textPadding) {
            this.textPadding = textPadding;
            BoomButton button = button();
            if (button != null) {
                button.textPadding = textPadding;
                button.updateTextPadding();
            }
        }
        return (T) this;
    }

    /**
     * Set the typeface of the inner_tv.
     *
     * @param typeface typeface
     * @return the builder
     */
    public T typeface(Typeface typeface) {
        this.typeface = typeface;
        return (T) this;
    }

    /**
     * Set the maximum of the lines of inner_tv-view.
     *
     * @param maxLines maximum lines
     * @return the builder
     */
    public T maxLines(int maxLines) {
        this.maxLines = maxLines;
        return (T) this;
    }

    /**
     * Set the gravity of inner_tv-view.
     *
     * @param gravity gravity, for example, Gravity.CENTER
     * @return the builder
     */
    public T textGravity(int gravity) {
        this.textGravity = gravity;
        return (T) this;
    }

    /**
     * Set the ellipsize of the inner_tv-view.
     *
     * @param ellipsize ellipsize
     * @return the builder
     */
    public T ellipsize(TextUtils.TruncateAt ellipsize) {
        this.ellipsize = ellipsize;
        return (T) this;
    }

    /**
     * Set the inner_tv size of the inner_tv-view.
     *
     * @param textSize size of inner_tv, in sp
     * @return the builder
     */
    public T textSize(int textSize) {
        this.textSize = textSize;
        return (T) this;
    }
}
