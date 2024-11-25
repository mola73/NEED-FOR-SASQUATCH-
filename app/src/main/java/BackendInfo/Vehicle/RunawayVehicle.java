package BackendInfo.Vehicle;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.example.needforsasquatch.R;

public class RunawayVehicle extends Vehicle {
    private ImageView mainCar;
    private Boolean hasNitro;

    public RunawayVehicle(ImageView mainCar) {
        this.mainCar = mainCar;
    }

    public ImageView getMainCar() {
        return mainCar;
    }

    public void setMainCar(ImageView mainCar) {
        this.mainCar = mainCar;
    }
}
