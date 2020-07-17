package com.banuba.sdk.example.beautification.effects.beauty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class ModelDataKeys {
    static final String MORPH_CHEEKS = "morph_cheeks_str";
    static final String MORPH_EYES = "morph_eyes_str";
    static final String MORPH_NOSE = "morph_nose_str";

    static final String SKIN_SOFT = "skin_soft_str";
    static final String SOFTLIGHT_ALPHA = "softlight_alpha";
    static final String SOFTLIGHT_TEX = "softlight_tex";

    static final String EYES_FLARE_ALPHA = "eye_flare_alpha";
    static final String EYES_COLORING = "eyes_coloring_str";

    static final String TEETH_WHITENING = "teeth_whitening_str";

    static final String LASHES_TEX = "lashes_tex";
    static final String LASHES_ALPHA = "lashes_alpha";

    static final String EYEBROWS_TEX = "eyebrows_tex";
    static final String EYEBROWS_ALPHA = "eyebrows_alpha";

    static final String FINAL_COLOR_CORRECTION = "final_color_correction_str";
    static final String FINAL_COLOR_CORRECTION_TEX = "final_color_correction_tex";
}

final class VSModel {
    private Map<String, List<ValueSetter>> mModel;

    VSModel(ValueSetterListener valueListener) {
        mModel = new HashMap<String, List<ValueSetter>>() {
            {
                put("Morph", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarValueSetter(
                            "Cheeks", ModelDataKeys.MORPH_CHEEKS, valueListener, 2));
                        add(new SeekBarValueSetter(
                            "Eyes", ModelDataKeys.MORPH_EYES, valueListener, 2));
                        add(new SeekBarValueSetter(
                            "Nose", ModelDataKeys.MORPH_NOSE, valueListener, 2));
                    }
                });
                put("Skin", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarValueSetter(
                            "Softness", ModelDataKeys.SKIN_SOFT, valueListener, 2));
                        add(new SeekBarValueSetter(
                            "Softlight alpha", ModelDataKeys.SOFTLIGHT_ALPHA, valueListener));
                        add(new SpinnerValueSetter(
                            "Softlight tex", ModelDataKeys.SOFTLIGHT_TEX, new ArrayList<String>() {
                                {
                                    add("Soft"); // soft.ktx
                                }
                            }, valueListener));
                    }
                });
                put("Eyes", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarValueSetter(
                            "Flare alpha", ModelDataKeys.EYES_FLARE_ALPHA, valueListener));
                        add(new SeekBarValueSetter(
                            "Coloring", ModelDataKeys.EYES_COLORING, valueListener));
                    }
                });
                put("Teeth", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarValueSetter(
                            "Whitening", ModelDataKeys.TEETH_WHITENING, valueListener));
                    }
                });
                put("Lashes", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarValueSetter(
                            "Alpha", ModelDataKeys.LASHES_ALPHA, valueListener, 2));
                        add(new SpinnerValueSetter(
                            "Texture", ModelDataKeys.LASHES_TEX, new ArrayList<String>() {
                                {
                                    add("Lashes 1"); // lashes1.ktx
                                    add("Lashes 2");
                                    add("Lashes 3");
                                    add("Lashes 4");
                                    add("Lashes 5");
                                    add("Lashes 6");
                                    add("Lashes 7");
                                    add("Lashes 8");
                                    add("Lashes 9");
                                    add("Lashes 10");
                                    add("Lashes 11");
                                    add("Lashes 12");
                                }
                            }, valueListener));
                    }
                });
                put("Eye brows", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarValueSetter(
                            "Alpha", ModelDataKeys.EYEBROWS_ALPHA, valueListener, 2));
                        add(new SpinnerValueSetter(
                            "Texture", ModelDataKeys.EYEBROWS_TEX, new ArrayList<String>() {
                                {
                                    add("Neutral"); // brows_512.ktx
                                    add("Hi");      // brows_Hi_512.ktx
                                }
                            }, valueListener));
                    }
                });
                put("Color correction", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarValueSetter(
                            "Intensity", ModelDataKeys.FINAL_COLOR_CORRECTION, valueListener));
                        add(new SpinnerValueSetter(
                            "Texture",
                            ModelDataKeys.FINAL_COLOR_CORRECTION_TEX,
                            new ArrayList<String>() {
                                {
                                    add("Barbie");    // lut3d_barbie.png
                                    add("BWC");       // lut3d_bwc.png
                                    add("Columbia");  // lut3d_columbia.png
                                    add("Egg");       // lut3d_egg.png
                                    add("LimonTea");  // lut3d_limontea.png
                                    add("Milano");    // lut3d_milano.png
                                    add("Nash");      // lut3d_nash.png
                                    add("Pink vine"); // lut3d_pinkvine.png
                                    add("Pirate");    // lut3d_pirate.png
                                    add("Spark");     // lut3d_spark.png
                                    add("Spring");    // lut3d_spring.png
                                    add("Sunny");     // lut3d_sunny.png
                                    add("Vinyl");     // lut3d_vinyl.png
                                    add("Violla");    // lut3d_violla.png
                                    add("Yury");      // lut3d_yury.png
                                }
                            },
                            valueListener));
                    }
                });
            }
        };
    }

    /**
     * @param displayName Group name
     * @return Requested group items to use for effect value setters
     */
    List<ValueSetter> getGroup(String displayName) {
        return mModel.get(displayName);
    }

    /**
     * @return Effect names to show in selector
     */
    List<String> getGroupNames() {
        List<String> ret = new ArrayList<>(mModel.keySet());
        Collections.sort(ret);
        return ret;
    }
}
