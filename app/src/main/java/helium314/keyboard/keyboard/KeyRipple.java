package helium314.keyboard.keyboard;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;

import androidx.annotation.NonNull;

import helium314.keyboard.latin.settings.SettingsValues;

/**
 * Premium key ripple effect.
 */
public class KeyRipple {
    private static final long RIPPLE_DURATION_MS = 280;

    public final float x;
    public final float y;
    public final long startTime;
    public final int color;
    private final float maxRadius;
    private final float speedMultiplier;

    public KeyRipple(float x, float y, int color, float maxRadius, float speed) {
        this.x = x;
        this.y = y;
        this.startTime = SystemClock.uptimeMillis();
        this.color = color;
        this.maxRadius = maxRadius * 1.5f;
        this.speedMultiplier = speed;
    }

    public boolean isFinished() {
        long elapsed = SystemClock.uptimeMillis() - startTime;
        return elapsed > RIPPLE_DURATION_MS * speedMultiplier;
    }

    public void draw(@NonNull Canvas canvas, @NonNull Paint paint, SettingsValues sv) {
        long elapsed = SystemClock.uptimeMillis() - startTime;
        float progress = Math.min(1.0f, elapsed / (RIPPLE_DURATION_MS * speedMultiplier));

        float ease = 1.0f - (1.0f - progress) * (1.0f - progress);
        float radius = maxRadius * ease;
        float alpha = (1.0f - ease) * sv.mKeyRippleOpacity * 255;

        paint.setColor(color);
        paint.setAlpha((int) alpha);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, radius, paint);

        if ("premium".equals(sv.mKeyRippleStyle)) {
            paint.setAlpha((int) (alpha * 0.35f));
            canvas.drawCircle(x, y, radius * 0.55f, paint);
        }
    }
}