package gregtech.api.interfaces;

import javax.annotation.Nullable;

public interface ICleanroomReceiver {

    @Nullable
    ICleanroom getCleanroom();

    void setCleanroom(ICleanroom cleanroom);
}
