package com.banuba.sdk.example.beautification.effects.beauty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class ModelDataKeys {
    /* Skin */
    /** Sets the skin softening from 0 to 1 */
    static final String SKIN_SOFTENING = "Skin.softening";
    /** Sets skin color */
    static final String SKIN_COLOR = "Skin.color";


    /* Eyes */
    /** Sets the eyes sclera whitening strength from 0 to 1 */
    static final String EYES_WHITENING = "Eyes.whitening";
    /** Sets the eyes color */
    static final String EYES_COLOR = "Eyes.color";
    /** Sets the eyes flare strength from 0 to 1 */
    static final String EYES_FLARE = "Eyes.flare";


    /* Teeth */
    /** Sets the teeth whitening strength from 0 to 1 */
    static final String TEETH_WHITENING = "Teeth.whitening";


    /* Lips */
    /** Sets the lips matt color */
    static final String LIPS_MATT = "Lips.matt";
    /** Sets the lips shiny color */
    static final String LIPS_SHINY = "Lips.shiny";
    /** Sets the lips glitter color */
    static final String LIPS_GLITTER = "Lips.glitter";


    /* Makeup */
    /** Sets eyeliner color in R G B A format */
    static final String MAKEUP_EYELINER = "Makeup.eyeliner";
    /** Sets eyeshadow color in R G B A format */
    static final String MAKEUP_EYESHADOW = "Makeup.eyeshadow";
    /** Sets lashes color in R G B A format */
    static final String MAKEUP_LASHES = "Eyelashes.color";
    /** Sets highlighter color in R G B A format */
    static final String MAKEUP_HIGHLIGHTER = "Makeup.highlighter";
    /** Sets blushes color in R G B A format */
    static final String MAKEUP_BLUSHES = "Makeup.blushes";
    /** Sets contour color in R G B A format */
    static final String MAKEUP_CONTOUR = "Makeup.contour";
    /** Sets makeup texture */
    static final String MAKEUP_SET = "Makeup.set";


    /* Eyelashes */
    /** Sets eyelashes texture */
    static final String EYELASHES_TEXTURE = "Eyelashes.texture";


    /* Hair */
    /** Set hair color multiple colors (single color) */
    static final String HAIR_COLOR = "Hair.color";
    /** Sets hair strands multiple colors (up to 5 colors) */
    static final String HAIR_STRANDS = "Hair.strands";


    /* Softlight */
    /** Sets the softlight strength from 0 to 1 */
    static final String SOFTLIGHT_STRENGTH = "Softlight.strength";


    /* Filter */
    /** Sets the filter LUT ("lut_texture.png") */
    static final String FILTER_SET = "Filter.set";
    /** Sets the filter strength from 0 to 1 */
    static final String FILTER_STRENGTH = "Filter.strength";


    /* FaceMorph */
    /** Sets eyes grow strength from 0 to 1 */
    static final String FACE_MORPH_EYES = "FaceMorph.eyes";
    /** Sets nose shrink strength from 0 to 1 */
    static final String FACE_MORPH_NOSE = "FaceMorph.nose";
    /** Sets face (cheeks) shrink strength from 0 to 1 */
    static final String FACE_MORPH_FACE = "FaceMorph.face";
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
                                "Hair color (rgba)", ModelDataKeys.HAIR_COLOR, valueListener));
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
