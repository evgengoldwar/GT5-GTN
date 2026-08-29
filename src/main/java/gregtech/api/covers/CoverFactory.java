package gregtech.api.covers;

import gregtech.common.covers.Base.Cover;

@FunctionalInterface
public interface CoverFactory {

    Cover buildCover(CoverContext context);
}
