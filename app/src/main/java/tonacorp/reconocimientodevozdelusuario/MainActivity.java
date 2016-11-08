package tonacorp.reconocimientodevozdelusuario;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CODIGO_SOLICITUD = 1234;
    private ListView lista_palabras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton botonHablar = (ImageButton) findViewById(R.id.botonSpeak);
        lista_palabras = (ListView) findViewById(R.id.lista_palabras);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> actividades = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

        if(actividades.size() == 0){
            botonHablar.setEnabled(false);

        }


    }

    private void iniciarReconocimientoVoz (){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Reconocimiento de voz");
        startActivityForResult(i, CODIGO_SOLICITUD);
    }

    public void BotonHablar(View view){
        iniciarReconocimientoVoz();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CODIGO_SOLICITUD && requestCode == RESULT_OK){
            ArrayList<String> filas = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            lista_palabras.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filas));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
