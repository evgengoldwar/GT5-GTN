package gregtech.api.util;

import com.google.common.collect.HashBiMap;

import gregtech.api.enums.Materials_OLD;

public class PCBFactoryManager {

    private static final HashBiMap<Materials_OLD, Integer> mPlasticTiers = HashBiMap.create();
    public static int mTiersOfPlastics = 0;

    public static void addPlasticTier(Materials_OLD aMaterial, int aTier) {
        mPlasticTiers.put(aMaterial, aTier);
        mTiersOfPlastics++;
    }

    public static int getPlasticTier(Materials_OLD aMaterial) {
        return mPlasticTiers.get(aMaterial);
    }

    public static Materials_OLD getPlasticMaterialFromTier(int aTier) {
        return mPlasticTiers.inverse()
            .get(aTier);
    }
}
