package com.banuba.sdk.example.beautification.data;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.banuba.sdk.example.beautification.makeup.MakeupApi.ColoringApi;
import com.banuba.sdk.example.beautification.makeup.MakeupApi.MorphApi;
import com.banuba.sdk.example.beautification.makeup.MakeupApi.BeautyApi;
import com.banuba.sdk.example.beautification.makeup.MakeupApi.ClearingApi;

public final class DataRepository {
    public static final class Group {
        public final String text;
        public final List<Section> sections;

        public Group(String text, List<Section> sections) {
            this.text = text;
            this.sections = sections;
        }
    }

    public static final class Section {
        public final String text;
        public final ColoringApi coloring;
        public final MorphApi morphing;
        public final BeautyApi beauty;
        public final ClearingApi clearing;

        public Section(String text, ColoringApi coloring) {
            this(text, coloring, null, null, null);
        }

        public Section(String text, MorphApi morph) {
            this(text, null, morph, null, null);
        }

        public Section(String text, BeautyApi beauty) {
            this(text, null, null, beauty, null);
        }

        public Section(String text, ClearingApi clearing) {
            this(text, null, null, null, clearing);
        }

        private Section(String text, ColoringApi coloring, MorphApi morph, BeautyApi beauty, ClearingApi clearing) {
            this.text = text;
            this.coloring = coloring;
            this.morphing = morph;
            this.beauty = beauty;
            this.clearing = clearing;
        }
    }

    // clang-format off
    public final List<Group> groups;

    private DataRepository(List<Group> groups) {
        this.groups = groups;
    }

    public static List<Group> getGroupList() {
        return DataRepository.MAKEUP_DATA_REPOSITORY.groups;
    }

    @Nullable
    public static List<Section> getSectionList(int groupIndex) {
        return groupIndex == -1 ? null : getGroupList().get(groupIndex).sections;
    }

    @Nullable
    public static Section getSection(int groupIndex, int sectionIndex) {
        final List<Section> sectionList = getSectionList(groupIndex);
        return sectionIndex == -1 || sectionList == null ? null : sectionList.get(sectionIndex);
    }

    public static List<String> makeStringListFromGroupList(List<Group> groups) {
        final List<String> strings = new ArrayList<>();
        for (final Group group: groups) {
            strings.add(group.text);
        }
        return strings;
    }

    public static List<String> makeStringListFromSectionList(List<Section> sections) {
        final List<String> strings = new ArrayList<>();
        for (final Section section: sections) {
            strings.add(section.text);
        }
        return strings;
    }

    private static final DataRepository MAKEUP_DATA_REPOSITORY = new DataRepository(
        Arrays.asList(
            new Group("Hair",
                Arrays.asList(
                    new Section("Color", ColoringApi.HAIR_COLOR),
                    new Section("Strands", ColoringApi.HAIR_STRANDS)
                )
            ),
            new Group("Makeup",
                Arrays.asList(
                    new Section("Highlighter", ColoringApi.MAKEUP_HIGHLIGHTER),
                    new Section("Contour", ColoringApi.MAKEUP_CONTOUR),
                    new Section("Blushes", ColoringApi.MAKEUP_BLUSHES),
                    new Section("Eyeliner", ColoringApi.MAKEUP_EYELINER),
                    new Section("Eyeshadow", ColoringApi.MAKEUP_EYESHADOW),
                    new Section("Lashes", ColoringApi.MAKEUP_LASHES)
                )
            ),
            new Group("Eyebrows",
                Arrays.asList(
                    new Section("Color", ColoringApi.BROWS_COLOR),
                    new Section("Spacing", MorphApi.EYEBROWS_SPACING),
                    new Section("Height", MorphApi.EYEBROWS_HEIGHT),
                    new Section("Bend", MorphApi.EYEBROWS_BEND)
                )
            ),
            new Group("Eyes",
                Arrays.asList(
                    new Section("Color", ColoringApi.EYES_COLOR),
                    new Section("Flare", BeautyApi.EYES_FLARE),
                    new Section("Whitening", BeautyApi.EYES_WHITENING),
                    new Section("Rounding", MorphApi.EYES_ROUNDING),
                    new Section("Enlargement", MorphApi.EYES_ENLARGEMENT),
                    new Section("Height", MorphApi.EYES_HEIGHT),
                    new Section("Spacing", MorphApi.EYES_SPACING),
                    new Section("Squint", MorphApi.EYES_SQUINT),
                    new Section("Lower eyelid pos", MorphApi.EYES_LOWER_EYELID_POS),
                    new Section("Lower eyelid size", MorphApi.EYES_LOWER_EYELID_SIZE)
                )
            ),
            new Group("Nose",
                Arrays.asList(
                    new Section("Width", MorphApi.NOSE_WIDTH),
                    new Section("Length", MorphApi.NOSE_LENGTH),
                    new Section("Tip width", MorphApi.NOSE_TIP_WIDTH)
                )
            ),
            new Group("Lips",
                Arrays.asList(
                    new Section("Lipstick matt", ColoringApi.LIPS_MATT),
                    new Section("Lipstick shiny", ColoringApi.LIPS_SHINY),
                    new Section("Lipstick glitter", ColoringApi.LIPS_GLITTER),
                    new Section("Lipsliner color", ColoringApi.LIPSLINER_COLOR),
                    new Section("Teeth whitening", BeautyApi.TEETH_WHITENING),
                    new Section("Size", MorphApi.LIPS_SIZE),
                    new Section("Height", MorphApi.LIPS_HEIGHT),
                    new Section("Thickness", MorphApi.LIPS_THICKNESS),
                    new Section("Mouth size", MorphApi.LIPS_MOUTH_SIZE),
                    new Section("Smile", MorphApi.LIPS_SMILE),
                    new Section("Shape", MorphApi.LIPS_SHAPE)
                )
            ),
            new Group("Face",
                Arrays.asList(
                    new Section("Skin color", ColoringApi.SKIN_COLOR),
                    new Section("Skin softening", BeautyApi.SKIN_SOFTENING),
                    new Section("Softlight strength", BeautyApi.SOFTLIGHT_STRENGTH),
                    new Section("Narrowing", MorphApi.FACE_NARROWING),
                    new Section("V shape", MorphApi.FACE_V_SHAPE),
                    new Section("Cheeks narrowing", MorphApi.FACE_CHEEKS_NARROWING),
                    new Section("Cheekbones narrowing", MorphApi.FACE_CHEEKBONES_NARROWING),
                    new Section("Jaw narrowing", MorphApi.FACE_JAW_NARROWING),
                    new Section("Chin shortening", MorphApi.FACE_CHIN_SHORTENING),
                    new Section("Chin narrowing", MorphApi.FACE_CHIN_NARROWING),
                    new Section("Sunken cheeks", MorphApi.FACE_SUNKEN_CHEEKS),
                    new Section("Cheeks jaw narrowing", MorphApi.FACE_CHEEKS_JAW_NARROWING)
                )
            ),
            new Group("Reset all",
                Collections.singletonList(
                    new Section("Reset all", ClearingApi.ALL)
                ))
        )
    );
    // clang-format on
}
