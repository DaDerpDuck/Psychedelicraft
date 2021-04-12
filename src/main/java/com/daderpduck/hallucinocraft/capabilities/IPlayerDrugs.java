package com.daderpduck.hallucinocraft.capabilities;

import com.daderpduck.hallucinocraft.drugs.Drug;
import com.daderpduck.hallucinocraft.drugs.DrugInstance;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public interface IPlayerDrugs {
    void addDrugSource(DrugInstance drug);

    void removeDrugSource(DrugInstance drug);

    void setSources(List<DrugInstance> drugInstances);

    void clearDrugSources();

    List<DrugInstance> getDrugSources();

    void putActive(Drug drug, float effect);

    @Nullable
    Float getActive(Drug drug);

    void clearActives();

    void setActives(Map<Drug, Float> activeDrugs);

    Map<Drug, Float> getActiveDrugs();
}