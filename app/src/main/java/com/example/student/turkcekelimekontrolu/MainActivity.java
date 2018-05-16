package com.example.student.turkcekelimekontrolu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText userEditText; //Kullanıcının Girmiş olduğu metin
    String userInputText; //Atayacağımız String Değer
    String[] arrayText; //Atayacağımız Dizi
    double score;
    TextView kucukUnluUyumu, buyukUnluUyumu, kelimeSonu, ikiSessiz, TurkceHarf, totalScore, ikiSes;
    String[] kalinHarfler, inceHarfler, rakamlar, kelimeSonuHarfleri, sessizHarfler, sesliHarfler, yuvarlakUnluArray, darYuvarlakUnluArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userEditText = findViewById(R.id.userText); //Edit Text ilklemesi Yapıldı
        kucukUnluUyumu = findViewById(R.id.kucukUnluUyumuTv);
        buyukUnluUyumu = findViewById(R.id.buyukUnluUyumuTv);
        kelimeSonu = findViewById(R.id.kelimeSonuUyumuTv);
        ikiSes = findViewById(R.id.ikiSesliYanYanaTev);
        ikiSessiz = findViewById(R.id.ikiSessizTv);
        TurkceHarf = findViewById(R.id.turkceHarfTv);
        totalScore = findViewById(R.id.totalScore);

        sessizHarfler = new String[]{"b", "c", "ç", "d", "f", "g", "ğ", "h", "j", "k", "l", "m", "n", "p", "r", "s", "ş", "t", "v", "y", "z"};
        sesliHarfler = new String[]{"a", "ı", "i", "o", "u", "ü", "e", "ö"};
        yuvarlakUnluArray = new String[]{"o", "ö", "u", "ü"};
        darYuvarlakUnluArray = new String[]{"o", "ö", "ı", "i"};
        kalinHarfler = new String[]{"a", "ı", "o", "u"};//kalın seslileri bir diziye attık
        inceHarfler = new String[]{"e", "i", "ö", "ü"};//ince seslileri bir diziye attık
        rakamlar = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};//rakamları bir diziye attık
        kelimeSonuHarfleri = new String[]{"b", "c", "d", "g"};
    }

    public void OnClickRequest(View view) { //Butona tıklanıldığı anda çalışıcak kodlar
        userInputText = userEditText.getText().toString(); //EditText gelen veri stringe atandı
        score = 0; //Skoru 0 a eşitledik
        arrayText = new String[userInputText.length()]; //EditText gelen verinin boyutu kadar dizi oluşturuldu
        Boolean ikiKelimeGirmis = false;
        for (int i = 0; i < userInputText.length(); i++) { //Gelen String veriyi diziye atadık
            String ikiKelime = String.valueOf(userInputText.charAt(i));
            if (ikiKelime == null) {
                arrayText[i] = String.valueOf(userInputText.charAt(++i));
            } else
                arrayText[i] = String.valueOf(userInputText.charAt(i));

        }
        if (!ikiKelimeGirmis) {
            KucukUnluUyumuKontrolEt(userInputText, arrayText); //KucukUnlu Uyumu için metod oluşturuldu ve string değer ile dizi gönderildi
            BuyukUnluUyumuKontrolEt(userInputText, arrayText); //Büyük Ünlü Uyumu için metod oluşturuldu ve string değer ,dizi gönderildi
            KelimeSonuUyumuKontrolEt(userInputText, arrayText); //Kelime sonundaki bulunan harfleri kontrol eder
            IkiSesliYanYanaKontrolEt(userInputText, arrayText); //İki sesli harfin yan yana olma durumunu kontrol eder.
            KelimedeTurkceHarfKontrolEt(userInputText, arrayText);//Kelimede Türkçe harf olup olmadığını kontrol eder.
            IkıSessizYanYanaKontrolEt(userInputText, arrayText);
        } else {
            Toast.makeText(this, "Tek Kelime Giriniz Arada Boşluk Olmasın", Toast.LENGTH_SHORT).show(); //Tek kelime girilmemesi olayındaki hatayı verdik
        }
        totalScore.setText("Kelimenin Türkçe Puanı %" + score / 100);


    }

    private void IkıSessizYanYanaKontrolEt(String userInputText, String[] arrayText) {
        boolean sessizYanYanaMı = false;


        for (int x = 0; x < userInputText.length(); x++) {
            System.out.println("xdegeri" + x);
            if (x != arrayText.length - 1) {
                for (int a = 0; a < sessizHarfler.length; a++) {
                    System.out.println("adegeri" + a);
                    if (a != sessizHarfler.length - 1) {
                        //Burada ki if amacı dizinin son indisine geldiğinde x+1 null dönmemesi için
                        if (arrayText[x].equals(sessizHarfler[a])) {
                            //Burada ki if amacı dizinin son indisine geldiğinde x+1 null dönmemesi için
                            for (int b = 0; b < sessizHarfler.length - 1; b++) {
                                if (b != sessizHarfler.length - 1) {
                                    if (arrayText[x + 1].equals(sessizHarfler[b])) {
                                        System.out.println("sonuc" + sessizYanYanaMı);
                                        sessizYanYanaMı = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!sessizYanYanaMı) { //iki sesli yan yana ise

            ikiSessiz.setText("Hayır +10 puan ");
            score += 10;
            ikiSessiz.setBackgroundColor(Color.parseColor("#76FF03"));
        }

        if (sessizYanYanaMı) {
            ikiSessiz.setText("Evet -20 puan");
            score -= 20;
            ikiSessiz.setBackgroundColor(Color.parseColor("#D50000"));
        }

    }


    private void KelimedeTurkceHarfKontrolEt(String userInputText, String[] arrayText) {
        boolean TurkceHarfVarMi = false;
        for (int i = 0; i < userInputText.length(); i++) {

            if (arrayText[i].equals("ğ") || arrayText.equals("İ")) { //Türkçe harf kontrolü
                TurkceHarfVarMi = true;
            }
        }
        if (TurkceHarfVarMi) {
            TurkceHarf.setText("Var +5 puan");
            score += 5;
            System.out.println("TurkceHarf" + score);
            TurkceHarf.setBackgroundColor(Color.parseColor("#76FF03"));

        } else {
            TurkceHarf.setText("Yok - puan yok ");
            TurkceHarf.setBackgroundColor(Color.parseColor("#D50000"));
        }
    }

    private void IkiSesliYanYanaKontrolEt(String userInputText, String[] arrayText) {
        boolean sesliYanYanaMı = false;


        for (int x = 0; x < userInputText.length(); x++) {
            System.out.println("xdegeri" + x);
            if (x != arrayText.length - 1) {
                for (int a = 0; a < sesliHarfler.length; a++) {
                    System.out.println("adegeri" + a);
                    if (a != sesliHarfler.length - 1) {
                        //Burada ki if amacı dizinin son indisine geldiğinde x+1 null dönmemesi için
                        if (arrayText[x].equals(sesliHarfler[a])) {
                            //Burada ki if amacı dizinin son indisine geldiğinde x+1 null dönmemesi için
                            for (int b = 0; b < sesliHarfler.length - 1; b++) {
                                if (b != sesliHarfler.length - 1) {
                                    if (arrayText[x + 1].equals(sesliHarfler[b])) {
                                        System.out.println("sonuc" + sesliYanYanaMı);
                                        sesliYanYanaMı = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!sesliYanYanaMı) { //iki sesli yan yana ise

            ikiSes.setText("Hayır +15 puan");
            score += 15;
            ikiSes.setBackgroundColor(Color.parseColor("#76FF03"));
        }

        if (sesliYanYanaMı) {
            ikiSes.setText("Evet -30 puan");
            score -= 30;
            ikiSes.setBackgroundColor(Color.parseColor("#D50000"));
        }
    }

    private void KelimeSonuUyumuKontrolEt(String userInputText, String[] arrayText) {
        boolean bcdgVarMi = false;


        for (int x = 0; x < kalinHarfler.length; x++) {

            if (kelimeSonuHarfleri[x].equals(arrayText[userInputText.length() - 1])) { //Dizinin sonundaki kelimeyi kelimeSonuHarfleri Dizisinde arıyor
                bcdgVarMi = true;
                System.out.println("Kalın var");
            }
        }
        if (!bcdgVarMi) { //Bcdg Yoksa
            kelimeSonu.setText("Yok +10 puan");
            score += 10;
            System.out.println("Kelime Sonu" + score);
            kelimeSonu.setBackgroundColor(Color.parseColor("#76FF03"));
            //gizem@atolye15.com
        } else {
            kelimeSonu.setText("Var -10 puan");
            kelimeSonu.setBackgroundColor(Color.parseColor("#D50000"));
        }
    }

    private void BuyukUnluUyumuKontrolEt(String userInputText, String[] arrayText) {
        boolean incevarmi = false;
        boolean kalinvarmi = false;
        boolean rakamvarmi = false;
        for (int i = 0; i < userInputText.length(); i++) { //Dizi uzunluğu kadar kontrol
            System.out.println("dizi" + arrayText[i]);
            for (int x = 0; x < kalinHarfler.length; x++) {

                if (kalinHarfler[x].equals(arrayText[i])) {
                    kalinvarmi = true;                         //Kalın harf var mı kontrolü
                    System.out.println("Kalın var");
                }
            }
            for (int x = 0; x < inceHarfler.length; x++) {

                if (inceHarfler[x].equals(arrayText[i])) {
                    incevarmi = true;                          //İnce harf var mı kontrolü
                    System.out.println("İnce var");
                }
            }
            for (int x = 0; x < rakamlar.length; x++) {

                if (rakamlar[x].equals(arrayText[i])) {
                    rakamvarmi = true;                         //rakam var mı kontrolü
                    System.out.println("Rakam var");
                }
            }

        }
        if (rakamvarmi == true) {
            Toast.makeText(this, "Rakam Var -15 puan", Toast.LENGTH_SHORT).show();
            score -= 15;
        }
        if (kalinvarmi == true && incevarmi == true) {
            // hem kalın hem ince sesli varsa büyük ünlü uyumuna uymaz
            score -= 10;
            buyukUnluUyumu.setText("Uymaz -10 puan");
            buyukUnluUyumu.setBackgroundColor(Color.parseColor("#D50000"));

        } else if (kalinvarmi == true && incevarmi == false) {
            //sadece kalın sesli varsa büyük ünlü uyumuna uyar

            buyukUnluUyumu.setText("Uyuyor +20 puan");
            buyukUnluUyumu.setBackgroundColor(Color.parseColor("#76FF03"));

        } else if (incevarmi == true && kalinvarmi == false) {
            //sadece ince sesli varsa büyük ünlü uyumuna uyar

            buyukUnluUyumu.setText("Uyuyor +20 puan");
            buyukUnluUyumu.setBackgroundColor(Color.parseColor("#76FF03"));

        }
        if (buyukUnluUyumu.getText().toString().equals("Uyuyor +20 puan")) {
            score += 20;
            System.out.println("buyukUn" + score);
        }

    }

    private void KucukUnluUyumuKontrolEt(String userInputText, String[] arrayText) {
        int i, duzUnlu = 0, yuvarlakUnlu = 0, duzgenisUnlu = 0, yuvarlakdarUnlu = 0, yuvarlakgenisUnlu = 0;



        for (i = 0; i < userInputText.length(); i++) { //Text 'in uzunluğu kadar dönecek
            if (arrayText[i].equals("a") || arrayText[i].equals("e") || arrayText[i].equals("ı") || arrayText[i].equals("i")) {//Dizideki (a - e - ı - i ) düz ünlü bulunma olaylarına bakıyoruz

                if (arrayText[i].equals("a") || arrayText[i].equals("e") && yuvarlakUnlu != 0) {
                    duzgenisUnlu += 1; //Eğer ( a - e varsa düzgeniş ünlüyü arttırıyoruz yoksa düz ünlü var demektir)
                } else
                    duzUnlu += 1;
            }
            if (arrayText[i].equals("o") || arrayText[i].equals("ö") || arrayText[i].equals("u") || arrayText[i].equals("ü")) {//Yuvarlak ünlü (o-ö-u-ü bulunma olayına bakıyoruz.
                yuvarlakUnlu += 1; //her durumda yuvarlak ünlü bulunma durumu

                if (arrayText[i].equals("u") || arrayText[i].equals("ü")) {
                    yuvarlakdarUnlu += 1; //u veya ü bulunma durumu

                } else {
                    yuvarlakgenisUnlu += 1; //u veya ü yoksa
                }

            }
        }

        boolean baslat = true;

            if ((duzUnlu != 0 && yuvarlakUnlu == 0 )) { //Yuvarlak ünlü yoksa ve düz ünlü 0 dan farklıysa kelime k.ü.u uyar
                kucukUnluUyumu.setText("Uyuyor +20 puan");
                kucukUnluUyumu.setBackgroundColor(Color.parseColor("#76FF03"));
                baslat = false;
            }
            if (yuvarlakUnlu != 0 && duzUnlu == 0 && duzgenisUnlu != 0 ) { //Yuvarlak ünlü sıfırdan farklı , düz ünlü 0 ve düz geniş ünlü sıfırdan farklıysa uyar
                kucukUnluUyumu.setText("Uyuyor +20 puan");
                kucukUnluUyumu.setBackgroundColor(Color.parseColor("#76FF03"));
                baslat = false;
            }
            if (yuvarlakdarUnlu != 0 && duzUnlu == 0 && yuvarlakgenisUnlu != 0 ) { //Yuvarlakdar ünlü 0 dan farklı , düz ünlü 0 ve yuvarlak geniş ünlü sıfırdan farklıysa uyar
                kucukUnluUyumu.setText("Uyuyor +20 puan");
                kucukUnluUyumu.setBackgroundColor(Color.parseColor("#76FF03"));
                baslat = false;
            }
            if ((duzgenisUnlu != 0 || duzUnlu != 0) && (yuvarlakUnlu != 0) ) {
                kucukUnluUyumu.setText("Uymaz -10 puan ");
                score -= 10;
                kucukUnluUyumu.setBackgroundColor(Color.parseColor("#D50000"));
                baslat = false;
            }
            if (kucukUnluUyumu.getText().toString().equals("Uyuyor +20 puan")) {
                baslat = false;
                score += 20;
                System.out.println("Kucuk Unlu " + score);
            }

        if (baslat) {
            kucukUnluUyumu.setText("Uymaz -10 puan ");
            score -= 10;
            kucukUnluUyumu.setBackgroundColor(Color.parseColor("#D50000"));
        }


    }
}
