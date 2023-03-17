package masterous.si6a.kamusku.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import masterous.si6a.kamusku.R;
import masterous.si6a.kamusku.adapters.KamusViewAdapter;
import masterous.si6a.kamusku.databases.KamusHelper;
import masterous.si6a.kamusku.databinding.ActivityMainBinding;
import masterous.si6a.kamusku.models.Kamus;
import masterous.si6a.kamusku.utilities.ItemClickListener;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private KamusHelper kamusHelper;
    private KamusViewAdapter kamusViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        kamusHelper = new KamusHelper(this);
        kamusViewAdapter = new KamusViewAdapter();
        kamusViewAdapter.setOnItemClickListener(new ItemClickListener<Kamus>() {
            @Override
            public void onItemClick(Kamus data, int position) {
                Toast.makeText(MainActivity.this,
                        "Item " + position + " : " + data.getTitle(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(Kamus data, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Perhatian!");
                builder.setMessage("Anda memilih " + data.getTitle() +
                        ". Pilih perintah yang Anda inginkan");
                builder.setCancelable(false);
                builder.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        kamusHelper.open();
                        int deleted = kamusHelper.deleteData(data.getId());
                        if (deleted == -1) {
                            Toast.makeText(MainActivity.this,
                                    "Gagal menghapus data!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Berhasil menghapus data!", Toast.LENGTH_SHORT).show();
                            getAllData();
                        }
                    }
                });
                builder.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, UpdateKamusActivity.class);
                        intent.putExtra("EXTRA_KAMUS", data);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });

        binding.rvKamus.setLayoutManager(new LinearLayoutManager(this));
        binding.rvKamus.setAdapter(kamusViewAdapter);

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSearch = binding.etSearch.getText().toString();

                if (TextUtils.isEmpty(strSearch)) {
                    getAllData();
                } else {
                    kamusHelper.open();
                    ArrayList<Kamus> kamus = kamusHelper.getAllDataByTitle(strSearch);
                    kamusViewAdapter.setData(kamus);
                }
            }
        });

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddKamusActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllData();
    }

    private void getAllData() {
        kamusHelper.open();
        ArrayList<Kamus> kamus = kamusHelper.getAllData();
        kamusViewAdapter.setData(kamus);
    }
}