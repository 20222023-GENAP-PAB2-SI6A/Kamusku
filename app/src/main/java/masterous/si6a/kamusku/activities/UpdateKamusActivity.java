package masterous.si6a.kamusku.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import masterous.si6a.kamusku.databases.KamusHelper;
import masterous.si6a.kamusku.databinding.ActivityUpdateKamusBinding;
import masterous.si6a.kamusku.models.Kamus;

public class UpdateKamusActivity extends AppCompatActivity {
    private ActivityUpdateKamusBinding binding;
    private KamusHelper kamusHelper;
    private Kamus kamus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUpdateKamusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        kamusHelper = new KamusHelper(this);
        kamus = getIntent().getParcelableExtra("EXTRA_KAMUS");

        binding.etTitle.setText(kamus.getTitle());
        binding.etDescription.setText(kamus.getDescription());

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = kamus.getId();
                String title = binding.etTitle.getText().toString();
                String description = binding.etDescription.getText().toString();

                boolean canUpdate = true;
                if (TextUtils.isEmpty(title)) {
                    canUpdate = false;
                    binding.etTitle.setError("Title harus diisi!");
                }
                if (TextUtils.isEmpty(description)) {
                    canUpdate = false;
                    binding.etDescription.setError("Description harus diisi!");
                }

                if (canUpdate) {
                    kamusHelper.open();
                    Kamus kamus = new Kamus(id, title, description);
                    kamusHelper.updateData(kamus);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}