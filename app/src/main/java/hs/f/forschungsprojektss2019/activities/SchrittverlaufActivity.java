package hs.f.forschungsprojektss2019.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import hs.f.forschungsprojektss2019.R;
import hs.f.forschungsprojektss2019.dao.InAppDatabase;

public class SchrittverlaufActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schrittverlauf);
    }
}
