package com.cagla.catchthekenny;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timeText; // Zamanı göstermek için bir TextView
    TextView scoreText; // Skoru göstermek için bir TextView
    int score; // Oyuncunun skorunu tutmak için bir değişken
    ImageView[] imageArray; // ImageViews dizisi, resimleri tutmak için
    Handler handler; // ImageViews'ın görünürlüğünü kontrol etmek için bir Handler
    Runnable runnable; // ImageViews'ın görünürlüğünü değiştirmek için bir Runnable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // activity_main.xml dosyasını ayarla

        timeText = findViewById(R.id.timeText); // timeText TextView'ini ayarla
        scoreText = findViewById(R.id.scoreText); // scoreText TextView'ini ayarla

        // ImageViews dizisini activity_main.xml dosyasındaki ImageView'larla eşleştir
        imageArray = new ImageView[]{
                findViewById(R.id.imageView),
                findViewById(R.id.imageView2),
                findViewById(R.id.imageView3),
                findViewById(R.id.imageView4),
                findViewById(R.id.imageView5),
                findViewById(R.id.imageView6),
                findViewById(R.id.imageView7),
                findViewById(R.id.imageView8),
                findViewById(R.id.imageView9),
                findViewById(R.id.imageView10),
                findViewById(R.id.imageView11),
                findViewById(R.id.imageView12),
                findViewById(R.id.imageView13),
                findViewById(R.id.imageView14),
                findViewById(R.id.imageView15),
                findViewById(R.id.imageView16)
        };

        hideImages(); // Resimleri gizleyen bir metodu çağır

        score = 0; // Skoru başlangıçta sıfıra ayarla

        // 10 saniyelik bir geri sayım başlatan bir CountDownTimer oluştur
        new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time: " + millisUntilFinished / 1000); // Geri sayımı zaman TextView'inde göster
            }

            @Override
            public void onFinish() {
                timeText.setText("Time's Off!"); // Geri sayım tamamlandığında zamanı göster
                handler.removeCallbacks(runnable); // Handler'ı durdur
                // Tüm resimleri görünmez hale getir
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }
                // Yeniden başlatma için bir AlertDialog oluştur
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                alert.setTitle("Restart?"); // Başlık
                alert.setMessage("Are you sure to restart game?"); // Mesaj
                // Yeniden başlatmayı onaylamak için pozitif buton
                alert.setPositiveButton("YES!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Yeniden başlatmak için Intent'i çağır ve mevcut aktiviteyi sonlandır
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                // Yeniden başlatmayı reddetmek için negatif buton
                alert.setNegativeButton("NO!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Oyun bittiğinde bir Toast mesajı göster
                        Toast.makeText(MainActivity.this,"Game Over!", Toast.LENGTH_LONG).show();
                    }
                });
                alert.show(); // AlertDialog'u göster
            }
        }.start(); // Geri sayımı başlat
    }

    // Bir resme tıklandığında çağrılan metot, skoru artırır
    public void increaseScore(View view) {
        score++; // Skoru artır
        scoreText.setText("Score: " + score); // Skoru gösteren TextView'i güncelle
    }

    // Resimleri gizleyen metot
    public void hideImages() {
        // Handler ve Runnable'ı oluşturarak resimlerin görünürlüğünü değiştir
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // Tüm resimleri görünmez hale getir
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }
                // Rastgele bir resmi görünür hale getir
                Random random = new Random();
                int i = random.nextInt(imageArray.length);
                imageArray[i].setVisibility(View.VISIBLE);

                handler.postDelayed(this, 500); // Belirli bir süre sonra bu Runnable'ı tekrar çalıştır
            }
        };
        handler.post(runnable); // Handler'ı ve Runnable'ı başlat
    }
}
