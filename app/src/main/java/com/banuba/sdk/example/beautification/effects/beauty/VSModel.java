package com.banuba.sdk.example.beautification.effects.beauty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class ModelDataKeys {
    /* Skin */
    /** Sets the skin softening from 0 to 1 */
    static final String SKIN_SOFTENING = "makeup.Skin.softening";
    /** Sets skin color */
    static final String SKIN_COLOR = "makeup.Skin.color";


    /* Eyes */
    /** Sets the eyes sclera whitening strength from 0 to 1 */
    static final String EYES_WHITENING = "makeup.Eyes.whitening";
    /** Sets the eyes color */
    static final String EYES_COLOR = "makeup.Eyes.color";
    /** Sets the eyes flare strength from 0 to 1 */
    static final String EYES_FLARE = "makeup.Eyes.flare";


    /* Teeth */
    /** Sets the teeth whitening strength from 0 to 1 */
    static final String TEETH_WHITENING = "makeup.Teeth.whitening";


    /* Lips */
    /** Sets the lips matt color */
    static final String LIPS_MATT = "makeup.Lips.matt";
    /** Sets the lips shiny color */
    static final String LIPS_SHINY = "makeup.Lips.shiny";
    /** Sets the lips glitter color */
    static final String LIPS_GLITTER = "makeup.Lips.glitter";


    /* Makeup */
    /** Sets eyeliner color in R G B A format */
    static final String MAKEUP_EYELINER = "makeup.Makeup.eyeliner";
    /** Sets eyeshadow color in R G B A format */
    static final String MAKEUP_EYESHADOW = "makeup.Makeup.eyeshadow";
    /** Sets lashes color in R G B A format */
    static final String MAKEUP_LASHES = "makeup.Eyelashes.color";
    /** Sets highlighter color in R G B A format */
    static final String MAKEUP_HIGHLIGHTER = "makeup.Makeup.highlighter";
    /** Sets blushes color in R G B A format */
    static final String MAKEUP_BLUSHES = "makeup.Makeup.blushes";
    /** Sets contour color in R G B A format */
    static final String MAKEUP_CONTOUR = "makeup.Makeup.contour";
    /** Sets makeup texture */
    static final String MAKEUP_SET = "makeup.Makeup.set";


    /* Eyelashes */
    /** Sets eyelashes texture */
    static final String EYELASHES_TEXTURE = "makeup.Eyelashes.texture";


    /* Hair */
    /** Set hair color multiple colors (up to 5 colors) */
    static final String HAIR_COLOR = "makeup.Hair.color";
    /** Sets hair strands multiple colors (up to 5 colors) */
    static final String HAIR_STRANDS = "makeup.Hair.strands";


    /* Softlight */
    /** Sets the softlight strength from 0 to 1 */
    static final String SOFTLIGHT_STRENGTH = "makeup.Softlight.strength";


    /* Filter */
    /** Sets the filter LUT ("lut_texture.png") */
    static final String FILTER_SET = "makeup.Filter.set";
    /** Sets the filter strength from 0 to 1 */
    static final String FILTER_STRENGTH = "makeup.Filter.strength";


    /* FaceMorph */
    /** Sets eyes grow strength from 0 to 1 */
    static final String FACE_MORPH_EYES = "makeup.FaceMorph.eyes";
    /** Sets nose shrink strength from 0 to 1 */
    static final String FACE_MORPH_NOSE = "makeup.FaceMorph.nose";
    /** Sets face (cheeks) shrink strength from 0 to 1 */
    static final String FACE_MORPH_FACE = "makeup.FaceMorph.face";


    /* EyeBagsRemoval */
    /** Sets EyeBagsRemoval to enable */
    static final String EYE_BAGS_REMOVAL_ENABLE = "makeup.EyeBagsRemoval.enable";
    /** Sets EyeBagsRemoval to disable */
    static final String EYE_BAGS_REMOVAL_DISABLE = "makeup.EyeBagsRemoval.disable";
}

final class VSModel {
    private Map<String, List<ValueSetter>> mModel;

    VSModel(ModelDataListener valueListener) {
        mModel = new HashMap<String, List<ValueSetter>>() {
            {
                put("Morph", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarFloatValueSetter(
                            "Face", ModelDataKeys.FACE_MORPH_FACE, valueListener));
                        add(new SeekBarFloatValueSetter(
                            "Eyes", ModelDataKeys.FACE_MORPH_EYES, valueListener));
                        add(new SeekBarFloatValueSetter(
                            "Nose", ModelDataKeys.FACE_MORPH_NOSE, valueListener));
                    }
                });
                put("Skin", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarFloatValueSetter(
                            "Softness", ModelDataKeys.SKIN_SOFTENING, valueListener));
                        add(new SeekBarRgbaValueSetter(
                            "Color", ModelDataKeys.SKIN_COLOR, valueListener));
                    }
                });
                put("Eyes", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarFloatValueSetter(
                            "Whitening", ModelDataKeys.EYES_WHITENING, valueListener));
                        add(new SeekBarRgbaValueSetter(
                            "Color", ModelDataKeys.EYES_COLOR, valueListener));
                        add(new SeekBarFloatValueSetter(
                            "Flare", ModelDataKeys.EYES_FLARE, valueListener));
                    }
                });

                put("Makeup eyeliner", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarRgbaValueSetter(
                            "Color", ModelDataKeys.MAKEUP_EYELINER, valueListener));
                        add(new LoadImageButtonValueSetter(
                                "Load eyeliner image", ModelDataKeys.MAKEUP_EYELINER, valueListener));
                    }
                });
                put("Makeup eyeshadow", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarRgbaValueSetter(
                            "Color", ModelDataKeys.MAKEUP_EYESHADOW, valueListener));
                        add(new LoadImageButtonValueSetter(
                            "Load eyeshadow image", ModelDataKeys.MAKEUP_EYESHADOW, valueListener));
                    }
                });
                put("Makeup texture", new ArrayList<ValueSetter>() {
                    {
                        add(new LoadImageButtonValueSetter(
                            "Load makeup image", ModelDataKeys.MAKEUP_SET, valueListener));
                    }
                });
                put("Makeup lashes", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarRgbaValueSetter(
                            "Color", ModelDataKeys.MAKEUP_LASHES, valueListener));
                        add( new LoadImageButtonValueSetter(
                            "Load lashes image", ModelDataKeys.EYELASHES_TEXTURE, valueListener));
                    }
                });
                put("Makeup highlighter", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarRgbaValueSetter(
                            "Color", ModelDataKeys.MAKEUP_HIGHLIGHTER, valueListener));
                        add(new LoadImageButtonValueSetter(
                                "Load highlighter image", ModelDataKeys.MAKEUP_HIGHLIGHTER, valueListener));
                    }
                });
                put("Makeup blushes", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarRgbaValueSetter(
                            "Color", ModelDataKeys.MAKEUP_BLUSHES, valueListener));
                        add(new LoadImageButtonValueSetter(
                            "Load blushes image", ModelDataKeys.MAKEUP_BLUSHES, valueListener));
                    }
                });
                put("Makeup contour", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarRgbaValueSetter(
                            "Color", ModelDataKeys.MAKEUP_CONTOUR, valueListener));
                        add(new LoadImageButtonValueSetter(
                            "Load contour image", ModelDataKeys.MAKEUP_CONTOUR, valueListener));
                    }
                });

                put("lips matt", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarRgbaValueSetter(
                            "Color", ModelDataKeys.LIPS_MATT, valueListener));
                    }
                });
                put("lips shiny", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarRgbaValueSetter(
                            "Color", ModelDataKeys.LIPS_SHINY, valueListener));
                    }
                });
                put("lips glitter", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarRgbaValueSetter(
                            "Color", ModelDataKeys.LIPS_GLITTER, valueListener));
                    }
                });

                put("Hair color", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarRgbaValueSetter(
                                "Color gradient", ModelDataKeys.HAIR_COLOR, valueListener, 0));
                        add(new SeekBarRgbaValueSetter(
                                null, ModelDataKeys.HAIR_COLOR, valueListener, 1));
                        add(new SeekBarRgbaValueSetter(
                                null, ModelDataKeys.HAIR_COLOR, valueListener, 2));
                        add(new SeekBarRgbaValueSetter(
                                null, ModelDataKeys.HAIR_COLOR, valueListener, 3));
                        add(new SeekBarRgbaValueSetter(
                                null, ModelDataKeys.HAIR_COLOR, valueListener, 4));
                    }
                });
                put("Hair strands", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarRgbaValueSetter(
                                "Color gradient", ModelDataKeys.HAIR_STRANDS, valueListener, 0));
                        add(new SeekBarRgbaValueSetter(
                                null, ModelDataKeys.HAIR_STRANDS, valueListener, 1));
                        add(new SeekBarRgbaValueSetter(
                                null, ModelDataKeys.HAIR_STRANDS, valueListener, 2));
                        add(new SeekBarRgbaValueSetter(
                                null, ModelDataKeys.HAIR_STRANDS, valueListener, 3));
                        add(new SeekBarRgbaValueSetter(
                                null, ModelDataKeys.HAIR_STRANDS, valueListener, 4));
                    }
                });

                put("Teeth", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarFloatValueSetter(
                            "Whitening", ModelDataKeys.TEETH_WHITENING, valueListener));
                    }
                });
                put("Softlight", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarFloatValueSetter(
                            "Softlight", ModelDataKeys.SOFTLIGHT_STRENGTH, valueListener));
                    }
                });
                put("Filter", new ArrayList<ValueSetter>() {
                    {
                        add(new SeekBarFloatValueSetter(
                            "Strength", ModelDataKeys.FILTER_STRENGTH, valueListener));
                        add( new LoadImageButtonValueSetter(
                            "Load an image", ModelDataKeys.FILTER_SET, valueListener));

                    }
                });
                put("Eye bags", new ArrayList<ValueSetter>() {
                    {
                        add(new SwitchValueSetter(
                            "Eye bags remove", ModelDataKeys.EYE_BAGS_REMOVAL_ENABLE,
                            ModelDataKeys.EYE_BAGS_REMOVAL_DISABLE, valueListener));
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
