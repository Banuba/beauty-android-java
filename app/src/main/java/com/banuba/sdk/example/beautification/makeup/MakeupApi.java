package com.banuba.sdk.example.beautification.makeup;

import android.graphics.Color;

import androidx.annotation.NonNull;

public class MakeupApi {
    public interface IJavaScriptCallback {
        void onEvalJs(String jsCode);
    }

    public enum ColoringApi {
        SKIN_COLOR("Skin.color"),
        HAIR_STRANDS("Hair.strands", true),
        HAIR_COLOR("Hair.color", true),
        EYES_COLOR("Eyes.color"),
        MAKEUP_HIGHLIGHTER("Makeup.highlighter"),
        MAKEUP_CONTOUR("Makeup.contour"),
        MAKEUP_BLUSHES("Makeup.blushes"),
        MAKEUP_EYELINER("Makeup.eyeliner"),
        MAKEUP_EYESHADOW("Makeup.eyeshadow"),
        MAKEUP_LASHES("Makeup.lashes"),
        BROWS_COLOR("Brows.color"),
        LIPS_MATT("Lips.matt"),
        LIPS_SHINY("Lips.shiny"),
        LIPS_GLITTER("Lips.glitter"),
        LIPSLINER_COLOR("LipsLiner.color");

        final String mMethodName;
        final boolean mUpToFiveColors;

        ColoringApi(String methodName) {
            mUpToFiveColors = false;
            mMethodName = methodName;
        }

        ColoringApi(String methodName, boolean upTpFiveColor) {
            mUpToFiveColors = upTpFiveColor;
            mMethodName = methodName;
        }

        public String methodName() {
            return mMethodName;
        }

        public boolean upToFiveColors() {
            return mUpToFiveColors;
        }
    }

    public enum BeautyApi {
        TEETH_WHITENING("Teeth.whitening"),
        SKIN_SOFTENING("Skin.softening"),
        EYES_FLARE("Eyes.flare"),
        EYES_WHITENING("Eyes.whitening"),
        SOFTLIGHT_STRENGTH("Softlight.strength");

        final String mMethodName;

        BeautyApi(String methodName) {
            mMethodName = methodName;
        }

        public String methodName() {
            return mMethodName;
        }
    }

    public enum MorphApi {
        EYEBROWS_SPACING(-1.0f),
        EYEBROWS_HEIGHT(-1.0f),
        EYEBROWS_BEND(-1.0f),

        EYES_ROUNDING,
        EYES_ENLARGEMENT,
        EYES_HEIGHT(-1.0f),
        EYES_SPACING(-1.0f),
        EYES_SQUINT(-1.0f),
        EYES_LOWER_EYELID_POS(-1.0f),
        EYES_LOWER_EYELID_SIZE(-1.0f),

        NOSE_WIDTH(-1.0f),
        NOSE_LENGTH(-1.0f),
        NOSE_TIP_WIDTH,

        LIPS_SIZE(-1.0f),
        LIPS_HEIGHT(-1.0f),
        LIPS_THICKNESS(-1.0f),
        LIPS_MOUTH_SIZE(-1.0f),
        LIPS_SMILE,
        LIPS_SHAPE(-1.0f),

        FACE_NARROWING,
        FACE_V_SHAPE,
        FACE_CHEEKS_NARROWING,
        FACE_CHEEKBONES_NARROWING(-1.0f),
        FACE_JAW_NARROWING,
        FACE_CHIN_SHORTENING,
        FACE_CHIN_NARROWING,
        FACE_SUNKEN_CHEEKS,
        FACE_CHEEKS_JAW_NARROWING;

        private final String mMethodName;
        private final String mParamName;
        private final float mMin;

        MorphApi() {
            this(0.0f);
        }

        MorphApi(float min) {
            //  The enum name contains the name of the method and the name of the parameter,
            //  separated by an underscore '_'
            final int indexOfUnderscore = name().indexOf('_');
            assert indexOfUnderscore != -1;
            mMethodName = "FaceMorph." + name().substring(0, indexOfUnderscore).toLowerCase();
            mParamName = name().substring(indexOfUnderscore + 1).toLowerCase();
            mMin = min;
        }

        public String methodName() {
            return mMethodName;
        }

        public String paramName() {
            return mParamName;
        }

        public float min() {
            return mMin;
        }

        public float max() {
            return 1.0f;
        }
    }

    public enum FilterApi {
        SET,      // string value (path)
        STRENGTH; // float value

        private final String mMethodName;

        FilterApi() {
            mMethodName = "Filter." + name().toLowerCase();
        }

        public String methodName() {
            return mMethodName;
        }
    }

    public enum ClearingApi {
        ALL,
        SOFTLIGHT,
        BROWS,
        MAKEUP,
        LIPS,
        LIPSLINER("LipsLiner"),
        FACEMORPH("FaceMorph"),
        FILTER,
        SKIN,
        HAIR,
        EYES;

        private final String mMethodName;

        public String methodName() {
            return mMethodName;
        }

        ClearingApi() {
            mMethodName = name().charAt(0) + name().substring(1).toLowerCase() + ".clear";
        }

        ClearingApi(String methodName) {
            mMethodName = methodName + ".clear";
        }
    }

    public final class JsBuilder {
        public JsBuilder set(ColoringApi method, int color) {
            return set(method, new int[] {color}, 1);
        }

        public JsBuilder set(ColoringApi method, int[] colors, int len) {
            mStringBuilder
                .append(method.methodName())
                .append("(");
            for (int i = 0; i < len; i++) {
                final int color = colors[i];
                final float r = Color.red(color) / 255.0f;
                final float g = Color.green(color) / 255.0f;
                final float b = Color.blue(color) / 255.0f;
                final float a = Color.alpha(color) / 255.0f;
                mStringBuilder
                    .append("'")
                    .append(r)
                    .append(" ")
                    .append(g)
                    .append(" ")
                    .append(b)
                    .append(" ")
                    .append(a)
                    .append("'");
                if (i + 1 < len) {
                    mStringBuilder.append(",");
                }
            }
            mStringBuilder
                .append(");\n");
            return this;
        }

        public JsBuilder set(BeautyApi method, float value) {
            mStringBuilder
                .append(method.methodName())
                .append("(")
                .append(value)
                .append(");\n");
            return this;
        }

        public JsBuilder set(MorphApi morph, float value) {
            mStringBuilder
                .append(morph.methodName())
                .append("({")
                .append(morph.paramName())
                .append(":")
                .append(value)
                .append("});\n");
            return this;
        }

        public JsBuilder set(FilterApi filter, String path) {
            if (filter != FilterApi.SET) {
                throw new RuntimeException("The " + filter.methodName() + " takes only a path as input.");
            }
            mStringBuilder
                .append(filter.methodName())
                .append("(\"")
                .append(path)
                .append("\");\n");
            return this;
        }

        public JsBuilder set(FilterApi filter, float value) {
            if (filter != FilterApi.STRENGTH) {
                throw new RuntimeException("The " + filter.methodName() + " takes only a float as input.");
            }
            mStringBuilder
                .append(filter.methodName())
                .append("(")
                .append(value)
                .append(");\n");
            return this;
        }

        public JsBuilder clear(ClearingApi clear) {
            if (clear == ClearingApi.ALL) {
                final ClearingApi[] values = ClearingApi.values();
                for (int i = 1; i < values.length; i++) {
                    mStringBuilder
                        .append(values[i].methodName())
                        .append("();\n");
                }
            } else {
                mStringBuilder
                    .append(clear.methodName())
                    .append("();\n");
            }
            return this;
        }

        public void call() {
            mCallback.onEvalJs(mStringBuilder.toString());
            mStringBuilder.setLength(0);
        }
    }

    // Preallocated buffer for formatting result strings for callJs method (facilitating GC work).
    private final StringBuilder mStringBuilder = new StringBuilder();

    private final IJavaScriptCallback mCallback;

    private final JsBuilder mJsBuilder = new JsBuilder();

    public MakeupApi(@NonNull IJavaScriptCallback callback) {
        this.mCallback = callback;
    }

    public JsBuilder getJsBuilder() {
        return mJsBuilder;
    }
}
