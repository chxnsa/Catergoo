# Aplikasi Catergoo

Catergoo adalah aplikasi berbasis JavaFX yang dirancang untuk memudahkan pemesanan katering untuk berbagai acara. Aplikasi ini menerapkan prinsip-prinsip Object-Oriented Programming (OOP), memiliki beberapa scene untuk interaksi pengguna, dan menyertakan thread untuk notifikasi. Data dikelola tanpa database, menggunakan menu default dan penyimpanan berbasis sesi.

## Cara Menjalankan Aplikasi

### Prasyarat

- **Java Development Kit (JDK)**: Versi 11 atau lebih tinggi (aku tidak tau versi brapa yg di perlu)
- **Gradle**: Versi yang kompatibel dengan proyek (dikonfigurasi di `gradle-wrapper.properties`)
- **JavaFX SDK**: Versi 17.0.2 (ditentukan di `build.gradle`)

### Langkah Penyiapan

1. **Kloning Repositori**:

   ```bash
   git clone https://github.com/chxnsa/Catergoo.git
   cd catergoo
   ```

2. **Instalasi Dependensi**:
   Pastikan Gradle terinstal, lalu jalankan:

   ```bash
   gradle build
   ```

   Perintah ini akan mengunduh semua dependensi, termasuk JavaFX dan pustaka pengujian.

3. **Jalankan Aplikasi**:
   Gunakan Gradle untuk menjalankan aplikasi:

   ```bash
   gradle run
   ```

   Alternatifnya, setelah build, jalankan kelas utama secara langsung:

   ```bash
   java --module-path /path/to/javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml -cp build/libs/catergoo-1.0.0.jar com.catergoo.CatergooApplication
   ```

4. **Jalankan Pengujian**:
   Eksekusi unit test dengan:
   ```bash
   gradle test
   ```
   Pengujian menggunakan JUnit 5, Mockito, dan TestFX untuk pengujian UI.

### Catatan Penyiapan Proyek

- Proyek menggunakan Gradle sebagai alat build, dengan konfigurasi di `build.gradle` dan `settings.gradle`.
- Modul JavaFX disertakan melalui plugin `org.openjfx.javafxplugin`.
- Pastikan path JavaFX SDK diatur dengan benar jika menjalankan secara manual.

# Panduan Penggunaan Aplikasi Catergoo

Aplikasi Catergoo memudahkan Anda memesan katering untuk berbagai acara. Berikut adalah panduan langkah demi langkah untuk menggunakan aplikasi ini

## 1. Memulai Aplikasi

1. **Buka Aplikasi**:
   - Jalankan aplikasi Catergoo. Anda akan disambut dengan halaman **Welcome** (Selamat Datang).
   - Halaman ini menampilkan pesan: _"Nikmati kemudahan memesan hidangan lezat dan bergizi untuk berbagai acara spesial Anda."_
2. **Pilih Opsi**:
   - Klik tombol **Masuk** untuk login jika sudah memiliki akun.
   - Klik tombol **Daftar** untuk membuat akun baru.

## 2. Registrasi Akun

1. **Akses Halaman Daftar**:
   - Dari halaman Welcome, klik **Daftar** untuk menuju halaman registrasi.
2. **Isi Formulir**:
   - Masukkan **Username** (minimal 5 karakter).
   - Masukkan **Kata Sandi** (minimal 8 karakter, dengan minimal 3 angka).
   - Pastikan input memenuhi validasi yang ditampilkan.
3. **Submit Registrasi**:
   - Klik tombol **Daftar** untuk membuat akun.
   - Jika sudah memiliki akun, klik tautan **Sudah punya akun? Masuk** untuk kembali ke halaman login.
4. **Hasil**:
   - Jika berhasil, Anda akan diarahkan ke halaman login.

## 3. Login ke Aplikasi

1. **Akses Halaman Masuk**:
   - Dari halaman Welcome, klik **Masuk**.
2. **Isi Formulir**:
   - Masukkan **Username** dan **Kata Sandi** yang telah didaftarkan.
3. **Submit Login**:
   - Klik tombol **Masuk** untuk masuk ke aplikasi.
   - Jika belum memiliki akun, klik tautan **Belum punya akun? Daftar** untuk menuju halaman registrasi.
4. **Hasil**:
   - Jika login berhasil, Anda akan masuk ke halaman **Home** aplikasi utama.

## 4. Memilih Menu (Home)

1. **Akses Halaman Home**:
   - Setelah login, Anda akan berada di halaman **Home**, yang menampilkan daftar menu seperti **Paket Daging**, **Paket Ayam Bakar**, **Paket Ayam Goreng**, dan **Paket Mix**.
2. **Filter Kategori**:
   - Gunakan filter di bagian atas (**Semua**, **Nasi Kotak**, **Kotak Snack**, **Paket**) untuk mempersempit pilihan menu.
3. **Pilih Item**:
   - Klik salah satu item menu (misalnya, **Paket Daging**) untuk membuka halaman **Item Detail/Customization**.

## 5. Mengkustomisasi dan Memesan Item

1. **Akses Halaman Kustomisasi**:
   - Setelah memilih item (misalnya, **Paket Daging**), Anda akan melihat detail seperti harga (Rp 30.000/pax), minimum order (100 pax), dan deskripsi menu.
2. **Pilih Opsi Kustomisasi**:
   - Pilih **Sayur** (misalnya, Capcay) dari dropdown.
   - Pilih **Lauk Tambahan** (misalnya, Tempe Oreg).
   - Pilih **Buah** (misalnya, Pisang).
   - Tentukan apakah ingin **Kerupuk** (pilih **Ya** atau **Tidak**).
3. **Atur Pemesanan**:
   - Masukkan **Jumlah Porsi** (minimal sesuai minimum order, misalnya 100 pax).
   - Pilih **Tanggal Pengantaran** menggunakan DatePicker (misalnya, 19/06/2025). Tanggal akan divalidasi berdasarkan ketersediaan dan jumlah pesanan (H-1 untuk ≤50 pax, H-2 untuk >50 pax).
   - Tambahkan **Catatan Khusus** jika ada (opsional).
4. **Lihat Total**:
   - Total pemesanan (misalnya, Rp 3.000.000 untuk 100 pax Paket Daging) akan ditampilkan secara dinamis.
5. **Tambah ke Keranjang**:
   - Klik tombol **Tambah ke Keranjang** untuk menyimpan pesanan ke keranjang. Tanggal pengantaran akan dipesan (booked) untuk item tersebut.

## 6. Mengelola Keranjang (Cart)

1. **Akses Halaman Keranjang**:
   - Klik tab **Keranjang** di navigasi atas.
2. **Lihat Isi Keranjang**:
   - Keranjang akan menampilkan item seperti:
     - **Paket Daging**: 100 pax, tanggal 19 Juni 2025, kustomisasi (Capcay, Tempe Oreg, Kerupuk, Pisang), subtotal Rp 3.000.000.
     - **Paket Risol**: 100 pax, tanggal 19 Juni 2025, kustomisasi (Bolu Coklat, Buras Ayam), subtotal Rp 900.000.
   - Total keseluruhan (misalnya, Rp 3.900.000) ditampilkan di bawah.
3. **Edit atau Hapus Item**:
   - Klik **Edit** untuk mengubah kustomisasi, jumlah, atau tanggal.
   - Klik **Hapus** untuk menghapus item (tanggal booking akan dilepaskan).
4. **Masukkan Alamat**:
   - Isi kolom **Alamat Pengiriman** untuk pengantaran.
5. **Konfirmasi Pemesanan**:
   - Klik tombol **Konfirmasi Pemesanan** untuk menuju halaman pembayaran.

## 7. Melakukan Pembayaran

1. **Akses Halaman Pembayaran**:
   - Setelah konfirmasi dari keranjang, Anda akan diarahkan ke halaman **Pembayaran**.
2. **Pilih Opsi Pembayaran**:
   - Pilih **Full Payment** atau **DP 30%**.
   - Nominal pembayaran (misalnya, Rp 3.900.000 untuk full payment) akan ditampilkan.
3. **Pilih Rekening Tujuan**:
   - Pilih bank untuk transfer (misalnya, **BCA 098123765341**).
   - Nomor rekening akan ditampilkan sesuai pilihan bank.
4. **Lakukan Transfer**:
   - Transfer sesuai nominal yang ditampilkan dan simpan bukti transaksi.
5. **Unggah Bukti Pembayaran**:
   - Klik **Upload Bukti Pembayaran** untuk mengunggah screenshot bukti transfer.
6. **Konfirmasi**:
   - Klik tombol **Konfirmasi Pembayaran** untuk menyelesaikan proses.
   - Pesanan akan ditambahkan ke riwayat, dan keranjang akan dikosongkan.
   - Tunggu konfirmasi dari sistem (maksimal 24 jam).

## 8. Melihat Riwayat Pesanan

1. **Akses Halaman Riwayat**:
   - Klik tab **Riwayat** di navigasi atas.
2. **Lihat Pesanan**:
   - Halaman ini menampilkan pesanan sebelumnya, misalnya:
     - **Paket Daging**: 100 pax, tanggal 19 Juni 2025, subtotal Rp 3.000.000, status **Menunggu Konfirmasi**.
     - **Paket Risol**: 100 pax, tanggal 19 Juni 2025, subtotal Rp 900.000, status **Menunggu Konfirmasi**.
3. **Detail Pesanan**:
   - Klik **Lihat Detail** untuk melihat kustomisasi dan informasi lengkap pesanan.
4. **Pembaruan Status**:
   - Status pesanan akan diperbarui secara otomatis oleh sistem (misalnya, dari "Menunggu Konfirmasi" ke "Diproses" atau "Selesai").

## 9. Logout

1. **Keluar dari Aplikasi**:
   - Klik tombol **Logout** di navigasi atas.
2. **Hasil**:
   - Anda akan kembali ke halaman **Welcome**.
   - Sesi pengguna akan diakhiri, dan Anda perlu login kembali untuk mengakses aplikasi.

## Catatan Penting

- **Validasi Tanggal**: Pastikan tanggal pengantaran yang dipilih tersedia dan sesuai dengan jumlah pesanan (H-1 atau H-2).
- **Kustomisasi**: Item seperti **Paket Daging** memungkinkan kustomisasi sayur, lauk, buah, dan kerupuk; pastikan semua opsi dipilih sebelum menambah ke keranjang.
- **Pembayaran**: Simpan bukti transfer untuk memudahkan konfirmasi. Unggah bukti dalam format gambar yang jelas.
- **Riwayat**: Gunakan halaman Riwayat untuk memantau status pesanan Anda.

Dengan mengikuti panduan ini, Anda dapat dengan mudah memesan katering melalui aplikasi Catergoo.

## Struktur Kode

Proyek ini memiliki struktur modular di bawah direktori `src/main/java/com/catergoo`, yang diorganisasi ke dalam paket untuk pemisahan tanggung jawab yang jelas:

1. **com.catergoo.model**:

   - Berisi kelas-kelas entitas utama:
     - `MenuItem.java`: Kelas abstrak untuk item menu.
     - `FoodPackage.java` & `SnackPackage.java`: Implementasi konkrit dari `MenuItem` dengan dukungan kustomisasi.
     - `CartItem.java`: Mewakili item di keranjang belanja.
     - `User.java`: Mengelola data pengguna, keranjang, dan riwayat pesanan.
     - `Order.java`: Mewakili pesanan yang telah dikonfirmasi.
     - `Customizable.java`: Interface untuk item menu yang dapat dikustomisasi.

2. **com.catergoo.manager**:

   - `SessionManager.java`: Mengelola sesi pengguna, registrasi, serta fungsi login/logout.
   - `MenuManager.java`: Mengelola menu default dan pengambilan item.

3. **com.catergoo.service**:

   - `NotificationService.java`: Thread latar belakang untuk pembaruan status pesanan secara berkala.

4. **com.catergoo.view**:

   - Kelas UI untuk berbagai scene:
     - `WelcomeView.java`, `LoginView.java`, `RegisterView.java`: Tampilan terkait otentikasi.
     - `HomeView.java`, `ItemDetailView.java`, `CartView.java`, `PaymentView.java`, `HistoryView.java`: Tampilan aplikasi utama.
     - `SceneManager.java`: Mengelola transisi scene.
   - Komponen kustom seperti `MenuItemCard.java`, `CartItemRow.java`, `CustomizationPanel.java`, dan `NavigationBar.java`.

5. **com.catergoo.util**:

   - Kelas utilitas: `ValidationUtil.java`, `DateUtil.java`, `UIUtil.java`, `Constants.java`.

6. **resources**:

   - Berisi stylesheet CSS, gambar, dan font untuk styling UI.

7. **test**:
   - Mencerminkan struktur main untuk pengujian unit.

### File Utama

- `CatergooApplication.java`: Titik masuk aplikasi JavaFX.
- `build.gradle`: Konfigurasi Gradle untuk dependensi dan tugas build.
- `module-info.java`: Konfigurasi modul Java untuk JavaFX.

## Penerapan Pilar OOP

Aplikasi Catergoo menerapkan empat pilar Object-Oriented Programming:

1. **Encapsulation**:

   - Semua atribut kelas bersifat private, dengan akses melalui getter dan setter publik.
   - Contoh: Di `MenuItem.java`, atribut seperti `itemName`, `pricePerPax`, dan `bookedDates` bersifat private, diakses melalui metode seperti `getItemName()` dan `isDateAvailable()`.

2. **Inheritance**:

   - `FoodPackage.java` dan `SnackPackage.java` mewarisi kelas abstrak `MenuItem.java`, mewarisi atribut dan metode umum.
   - Contoh: Kedua subkelas mewarisi `displayInfo()` dan mengoverride-nya untuk format spesifik.

3. **Abstraction**:

   - Kelas abstrak `MenuItem` mendefinisikan metode `displayInfo()`, yang diterapkan di subkelas.
   - Interface `Customizable` menetapkan metode (`getCustomizationPanel()`, `applyCustomizations()`, `getSelectedCustomizations()`) untuk item yang dapat dikustomisasi, diimplementasikan oleh `FoodPackage` dan `SnackPackage`.

4. **Polymorphism**:
   - **Method Overriding**: Subkelas mengoverride `displayInfo()` dan `getMinBookingDate()` untuk logika spesifik (misalnya, `H-2` untuk pesanan > 50, `H-1` untuk ≤ 50).
   - **Method Overloading**: Konstruktor `MenuItem` dan metode `calculatePrice()` mendukung konfigurasi parameter berbeda.
   - **Interface Polymorphism**: `FoodPackage` dan `SnackPackage` mengimplementasikan `Customizable`, memungkinkan penanganan polimorfik untuk logika kustomisasi.

## Catatan Tambahan

- **Implementasi Thread**: Kelas `NotificationService` menggunakan thread latar belakang untuk memeriksa dan memperbarui status pesanan secara berkala, menjaga responsivitas UI dengan `Platform.runLater()` untuk pembaruan UI.
- **Manajemen Sesi**: `SessionManager` mengisolasi data pengguna, memastikan setiap pengguna memiliki keranjang dan riwayat pesanan sendiri.
- **Sistem Booking**: Validasi tanggal booking secara real-time diimplementasikan di `MenuItem` dan `ItemDetailView`, dengan pembaruan dinamis pada `DatePicker` berdasarkan perubahan jumlah pesanan.
- **Tanpa Database**: Data dikelola dalam memori menggunakan `MenuManager` untuk menu default dan objek `User` untuk keranjang serta riwayat, memastikan kesederhanaan dan isolasi.

Untuk detail lebih lanjut, anda dapat menghubungi (email/atau apalah) . Terima kasih atas perhatian Anda! :D. [Klik untuk
