package com.banuba.sdk.example.beautification.effects.beauty;

import android.os.Parcelable;

import com.banuba.sdk.example.beautification.effects.beauty.SettersData.SettersData;
import com.banuba.sdk.example.beautification.effects.beauty.SettersData.SettersFileNameData;
import com.banuba.sdk.example.beautification.effects.beauty.SettersData.SettersFloatData;
import com.banuba.sdk.example.beautification.effects.beauty.SettersData.SettersRGBAData;

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

    VSModel(ModelDataListener valueListener, HashMap<String, ArrayList<Parcelable>> settersData) {
        mModel = new HashMap<String, List<ValueSetter>>() {
            {
                put("Morph", createMorphSettersList(settersData == null ? null: settersData.get("Morph"), valueListener));
                put("Skin", createSkinSettersList(settersData == null ? null: settersData.get("Skin"), valueListener));
                put("Eyes", createEyesSettersList(settersData == null ? null: settersData.get("Eyes"), valueListener));
                put("Hair color", createHairColorSettersList(settersData == null ? null: settersData.get("Hair color"), valueListener));
                put("Hair strands", createHairStrandsSettersList(settersData == null ? null: settersData.get("Hair strands"), valueListener));
                put("Filter", createFilterSettersList(settersData == null ? null: settersData.get("Filter"), valueListener));
                put("Makeup eyeliner", createMakeupEyelinerSettersList(settersData == null ? null: settersData.get("Makeup eyeliner"), valueListener));
                put("Makeup eyeshadow", createMakeupEyeshadowSettersList(settersData == null ? null: settersData.get("Makeup eyeshadow"), valueListener));
                put("Makeup texture", createMakeupTextureSettersList(settersData == null ? null: settersData.get("Makeup texture"), valueListener));
                put("Makeup lashes", createMakeupLashesSettersList(settersData == null ? null: settersData.get("Makeup lashes"), valueListener));
                put("Makeup highlighter", createMakeupHighlighterSettersList(settersData == null ? null: settersData.get("Makeup highlighter"), valueListener));
                put("Makeup blushes", createMakeupBlushesSettersList(settersData == null ? null: settersData.get("Makeup blushes"), valueListener));
                put("Makeup contour", createMakeupContourSettersList(settersData == null ? null: settersData.get("Makeup contour"), valueListener));
                put("lips matt", createLipsMattSettersList(settersData == null ? null: settersData.get("lips matt"), valueListener));
                put("lips shiny", createLipsShinySettersList(settersData == null ? null: settersData.get("lips shiny"), valueListener));
                put("lips glitter", createLipsGlitterSettersList(settersData == null ? null: settersData.get("lips glitter"), valueListener));
                put("Teeth", createTeethSettersList(settersData == null ? null: settersData.get("Teeth"), valueListener));
                put("Softlight", createSoftlightSettersList(settersData == null ? null: settersData.get("Softlight"), valueListener));
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

    HashMap<String, ArrayList<SettersData>> getSettersData() {
        HashMap<String, ArrayList<SettersData>> settersDataMap = new HashMap<>();
        for(Map.Entry<String, List<ValueSetter>> entry : mModel.entrySet()) {
            String name = entry.getKey();
            ArrayList<SettersData> settersData = new ArrayList<>();
            for(ValueSetter setter: entry.getValue()) {
                settersData.add(setter.getSettersData());
            }
            settersDataMap.put(name, settersData);
        }
        return settersDataMap;
    }

    List<ValueSetter> createMorphSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersFloatData faceData = null;
        SettersFloatData eyesData = null;
        SettersFloatData noseData = null;
        if (settersData != null) {
            faceData = (SettersFloatData) settersData.get(0);
            eyesData = (SettersFloatData) settersData.get(1);
            noseData = (SettersFloatData) settersData.get(2);
        }
        setterList.add(new SeekBarFloatValueSetter(
                "Face", ModelDataKeys.FACE_MORPH_FACE, valueListener, faceData, 0));
        setterList.add(new SeekBarFloatValueSetter(
                "Eyes", ModelDataKeys.FACE_MORPH_EYES, valueListener, eyesData, 1));
        setterList.add(new SeekBarFloatValueSetter(
                "Nose", ModelDataKeys.FACE_MORPH_NOSE, valueListener, noseData, 2));
        return setterList;
    }

    List<ValueSetter> createSkinSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersFloatData softness = null;
        SettersRGBAData color = null;
        if (settersData != null) {
            softness = (SettersFloatData) settersData.get(0);
            color = (SettersRGBAData) settersData.get(1);
        }
        setterList.add(new SeekBarFloatValueSetter(
                "Softness", ModelDataKeys.SKIN_SOFTENING, valueListener, softness, 0));
        setterList.add(new SeekBarRgbaValueSetter(
                "Color", ModelDataKeys.SKIN_COLOR, valueListener, color));
        return setterList;
    }

    List<ValueSetter> createEyesSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersFloatData whitening = null;
        SettersRGBAData color = null;
        SettersFloatData flare = null;
        if (settersData != null) {
            whitening = (SettersFloatData) settersData.get(0);
            color = (SettersRGBAData) settersData.get(1);
            flare  = (SettersFloatData) settersData.get(2);
        }
        setterList.add(new SeekBarFloatValueSetter(
                "Whitening", ModelDataKeys.EYES_WHITENING, valueListener, whitening, 0));
        setterList.add(new SeekBarRgbaValueSetter(
                "Color", ModelDataKeys.EYES_COLOR, valueListener, color));
        setterList.add(new SeekBarFloatValueSetter(
                "Flare", ModelDataKeys.EYES_FLARE, valueListener, flare, 1));
        return setterList;
    }

    List<ValueSetter> createMakeupEyelinerSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersRGBAData color = null;
        SettersFileNameData loadImage = null;
        if (settersData != null) {
            color = (SettersRGBAData) settersData.get(0);
            loadImage = (SettersFileNameData) settersData.get(1);
        }
        setterList.add(new SeekBarRgbaValueSetter(
                "Color", ModelDataKeys.MAKEUP_EYELINER, valueListener, color));
        setterList.add(new LoadImageButtonValueSetter(
                "Load eyeliner image", ModelDataKeys.MAKEUP_EYELINER, valueListener, loadImage));
        return setterList;
    }

    List<ValueSetter> createMakeupEyeshadowSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersRGBAData color = null;
        SettersFileNameData loadImage = null;
        if (settersData != null) {
            color = (SettersRGBAData) settersData.get(0);
            loadImage = (SettersFileNameData) settersData.get(1);
        }
        setterList.add(new SeekBarRgbaValueSetter(
                "Color", ModelDataKeys.MAKEUP_EYESHADOW, valueListener, color));
        setterList.add(new LoadImageButtonValueSetter(
                "Load eyeshadow image", ModelDataKeys.MAKEUP_EYESHADOW, valueListener, loadImage));
        return setterList;
    }

    List<ValueSetter> createMakeupTextureSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersFileNameData loadImage = null;
        if (settersData != null) {
            loadImage = (SettersFileNameData) settersData.get(0);
        }
        setterList.add(new LoadImageButtonValueSetter(
                "Load makeup image", ModelDataKeys.MAKEUP_SET, valueListener, loadImage));
        return setterList;
    }

    List<ValueSetter> createMakeupLashesSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersRGBAData color = null;
        SettersFileNameData loadImage = null;
        if (settersData != null) {
            color = (SettersRGBAData) settersData.get(0);
            loadImage = (SettersFileNameData) settersData.get(1);
        }
        setterList.add(new SeekBarRgbaValueSetter(
                "Color", ModelDataKeys.MAKEUP_LASHES, valueListener, color));
        setterList.add(new LoadImageButtonValueSetter(
                "Load lashes image", ModelDataKeys.EYELASHES_TEXTURE, valueListener, loadImage));
        return setterList;
    }

    List<ValueSetter> createMakeupHighlighterSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersRGBAData color = null;
        SettersFileNameData loadImage = null;
        if (settersData != null) {
            color = (SettersRGBAData) settersData.get(0);
            loadImage = (SettersFileNameData) settersData.get(1);
        }
        setterList.add(new SeekBarRgbaValueSetter(
                "Color", ModelDataKeys.MAKEUP_HIGHLIGHTER, valueListener, color));
        setterList.add(new LoadImageButtonValueSetter(
                "Load highlighter image", ModelDataKeys.MAKEUP_HIGHLIGHTER, valueListener, loadImage));
        return setterList;
    }

    List<ValueSetter> createMakeupBlushesSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersRGBAData color = null;
        SettersFileNameData loadImage = null;
        if (settersData != null) {
            color = (SettersRGBAData) settersData.get(0);
            loadImage = (SettersFileNameData) settersData.get(1);
        }
        setterList.add(new SeekBarRgbaValueSetter(
                "Color", ModelDataKeys.MAKEUP_BLUSHES, valueListener, color));
        setterList.add(new LoadImageButtonValueSetter(
                "Load blushes image", ModelDataKeys.MAKEUP_BLUSHES, valueListener, loadImage));
        return setterList;
    }

    List<ValueSetter> createMakeupContourSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersRGBAData color = null;
        SettersFileNameData loadImage = null;
        if (settersData != null) {
            color = (SettersRGBAData) settersData.get(0);
            loadImage = (SettersFileNameData) settersData.get(1);
        }
        setterList.add(new SeekBarRgbaValueSetter(
                "Color", ModelDataKeys.MAKEUP_CONTOUR, valueListener, color));
        setterList.add(new LoadImageButtonValueSetter(
                "Load contour image", ModelDataKeys.MAKEUP_CONTOUR, valueListener, loadImage));
        return setterList;
    }

    List<ValueSetter> createLipsMattSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersRGBAData color = null;
        if (settersData != null) {
            color = (SettersRGBAData) settersData.get(0);
        }
        setterList.add(new SeekBarRgbaValueSetter(
                "Color", ModelDataKeys.LIPS_MATT, valueListener, color));
        return setterList;
    }

    List<ValueSetter> createLipsShinySettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersRGBAData color = null;
        if (settersData != null) {
            color = (SettersRGBAData) settersData.get(0);
        }
        setterList.add(new SeekBarRgbaValueSetter(
                "Color", ModelDataKeys.LIPS_SHINY, valueListener, color));
        return setterList;
    }

    List<ValueSetter> createLipsGlitterSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersRGBAData color = null;
        if (settersData != null) {
            color = (SettersRGBAData) settersData.get(0);
        }
        setterList.add(new SeekBarRgbaValueSetter(
                "Color", ModelDataKeys.LIPS_GLITTER, valueListener, color));
        return setterList;
    }

    List<ValueSetter> createHairColorSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersRGBAData color = null;
        if (settersData != null) {
            color = (SettersRGBAData) settersData.get(0);
        }
        setterList.add(new SeekBarRgbaValueSetter(
                "Hair color (rgba)", ModelDataKeys.HAIR_COLOR, valueListener, color));
        return setterList;
    }

    List<ValueSetter> createHairStrandsSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        if(settersData == null) {
            setterList.add(new SeekBarRgbaValueSetter(
                    "Color gradient", ModelDataKeys.HAIR_STRANDS, valueListener, 0));
            setterList.add(new SeekBarRgbaValueSetter(
                    null, ModelDataKeys.HAIR_STRANDS, valueListener, 1));
            setterList.add(new SeekBarRgbaValueSetter(
                    null, ModelDataKeys.HAIR_STRANDS, valueListener, 2));
            setterList.add(new SeekBarRgbaValueSetter(
                    null, ModelDataKeys.HAIR_STRANDS, valueListener, 3));
            setterList.add(new SeekBarRgbaValueSetter(
                    null, ModelDataKeys.HAIR_STRANDS, valueListener, 4));
            return setterList;
        }

        SettersRGBAData color = (SettersRGBAData) settersData.get(0);
        SettersRGBAData color1 = (SettersRGBAData) settersData.get(1);
        SettersRGBAData color2 = (SettersRGBAData) settersData.get(2);
        SettersRGBAData color3 = (SettersRGBAData) settersData.get(3);
        SettersRGBAData color4 = (SettersRGBAData) settersData.get(4);
        setterList.add(new SeekBarRgbaValueSetter(
                "Color gradient", ModelDataKeys.HAIR_STRANDS, valueListener, color));
        setterList.add(new SeekBarRgbaValueSetter(
                null, ModelDataKeys.HAIR_STRANDS, valueListener, color1));
        setterList.add(new SeekBarRgbaValueSetter(
                null, ModelDataKeys.HAIR_STRANDS, valueListener, color2));
        setterList.add(new SeekBarRgbaValueSetter(
                null, ModelDataKeys.HAIR_STRANDS, valueListener, color3));
        setterList.add(new SeekBarRgbaValueSetter(
                null, ModelDataKeys.HAIR_STRANDS, valueListener, color4));
        return setterList;
    }

    List<ValueSetter> createTeethSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersFloatData value = null;
        if (settersData != null) {
            value = (SettersFloatData) settersData.get(0);
        }
        setterList.add(new SeekBarFloatValueSetter(
                "Whitening", ModelDataKeys.TEETH_WHITENING, valueListener, value, 0));
        return setterList;
    }

    List<ValueSetter> createSoftlightSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersFloatData value = null;
        if (settersData != null) {
            value = (SettersFloatData) settersData.get(0);
        }
        setterList.add(new SeekBarFloatValueSetter(
                "Softlight", ModelDataKeys.SOFTLIGHT_STRENGTH, valueListener, value, 0));
        return setterList;
    }

    List<ValueSetter> createFilterSettersList(ArrayList<Parcelable> settersData, ModelDataListener valueListener) {
        List<ValueSetter> setterList = new ArrayList<>();
        SettersFloatData strength = null;
        SettersFileNameData loadImage = null;
        if (settersData != null) {
            strength = (SettersFloatData) settersData.get(0);
            loadImage = (SettersFileNameData) settersData.get(1);
        }
        setterList.add(new SeekBarFloatValueSetter(
                "Strength", ModelDataKeys.FILTER_STRENGTH, valueListener, strength, 0));
        setterList.add(new LoadImageButtonValueSetter(
                "Load an image", ModelDataKeys.FILTER_SET, valueListener, loadImage));
        return setterList;
    }
}
