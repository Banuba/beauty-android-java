package com.banuba.sdk.example.beautification;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.banuba.sdk.example.beautification.data.DataRepository;
import com.banuba.sdk.example.beautification.data.DataRepository.Group;
import com.banuba.sdk.example.beautification.data.DataRepository.Section;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private final MutableLiveData<List<Group>> mMutableGroupList = new MutableLiveData<>();
    private final MutableLiveData<List<Section>> mMutableSectionList = new MutableLiveData<>();
    private final MutableLiveData<Section> mMutableSection = new MutableLiveData<>();

    private int mGroupIndex = -1;
    private final int[] mSectionIndices = new int[DataRepository.getGroupList().size()];
    private final HashMap<Integer, Integer> mMap = new HashMap<>();

    public MainActivityViewModel() {
        Arrays.fill(mSectionIndices, -1);
    }

    public LiveData<List<Group>> getGroupListLiveData() {
        loadGroupList();
        return mMutableGroupList;
    }

    public LiveData<List<Section>> getSectionListLiveData() {
        loadSectionList();
        return mMutableSectionList;
    }

    public LiveData<Section> getSectionLiveData() {
        loadSection();
        return mMutableSection;
    }

    private void loadGroupList() {
        mMutableGroupList.postValue(DataRepository.getGroupList());
    }

    private void loadSectionList() {
        mMutableSectionList.postValue(DataRepository.getSectionList(getCurrentGroupIndex()));
    }

    private void loadSection() {
        mMutableSection.postValue(DataRepository.getSection(getCurrentGroupIndex(), getCurrentSectionIndex()));
    }

    public int getCurrentGroupIndex() {
        return mGroupIndex;
    }

    public void setCurrentGroupIndex(int index) {
        mGroupIndex = index;
        loadSectionList();
        loadSection();
    }

    public int getCurrentSectionIndex() {
        return getCurrentGroupIndex() == -1 ? -1 : mSectionIndices[getCurrentGroupIndex()];
    }

    public void setCurrentSectionIndex(int index) {
        mSectionIndices[getCurrentGroupIndex()] = index;
        loadSection();
    }

    public int getValue(int key) {
        return getValue(key, 0);
    }

    public int getValue(int key, int defaultValue) {
        final Integer value = mMap.get(key);
        return value == null ? defaultValue : value;
    }

    public void setValue(int key, int value) {
        mMap.put(key, value);
    }

    public void clearValues() {
        mMap.clear();
    }
}
