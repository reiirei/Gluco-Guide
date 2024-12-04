# Cloud Computing
-

## URL
Landing page : -

## Deploy
- Clone this repository
- need preaper two services cloud storage & cloud sql / firestore
- don't forget to configuration auth in gcp

## Endpoint
### Register
#### Deskripsi : Mendaftarkan pengguna baru.
- Endpoint: /auth/register
- Method: POST
- Request Body: Content-Type: application/json
```
username as string
email as string
password as string

```
- Body (JSON)
```
{
  "name": "karakter-lebih-dari-12",
  "email": "fromat-email@example.com",
  "password": "karakter-lebih-dari-8"
}
```
- Respon Sukses
```
{
  "error": false,
  "message": "User Created"
}
```
- Respon Gagal (Username Kurang Dari 12 Karakter Dan Password Kurang Dari 8 Karakter)
```
{
  "error": true,
  "message": "Name must be at least 12 characters and password at least 8 characters"
}
```
- Respon Gagal (Email Sudah terdaftar)
```
{
  "error": true,
  "message": "Email already registered"
}
```

### Login
#### Deskripsi : Login pengguna untuk mendapatkan token JWT.
- Endpoint: /auth/login
- Method: POST
- Request Body: Content-Type: application/json
```
email as string
password as string

```
- Body (JSON)
```
{
  "email": "jaki@example.com",
  "password": "password123"
}
```
- Respon Sukses
```
{
  "error": false,
  "message": "Login success",
  "loginResult": {
    "userId": 1,
    "name": "Jakikenjeran",
    "token": "<JWT_TOKEN>"
  }
}
```
- Respon Gagal (Email Atau Password Salah)
```
{
  "error": true,
  "message": "Invalid email or password"
}
```

### Show User Profile
#### Deskripsi : Mendapatkan profil pengguna saat ini.
- Endpoint: /profile
- Method: GET
- Request Body: Authorization: Bearer <JWT_TOKEN>

- Respon Sukses:
```
{
  "error": false,
  "user": {
    "name": "Jakikenjeran",
    "email": "jaki326@example.com"
  }
}
```
- Respon Gagal (Token tidak valid/missing):
```
{
  "error": true,
  "message": "Token missing"
}
```


### Update User Profile
#### Deskripsi : Memperbarui profil pengguna.
- Endpoint: /profile
- Method: PUT
- Request Body:
  - Content-Type: application/json
  - Authorization: Bearer <JWT_TOKEN>
- Body (JSON):
```
{
  "name": "jakiUpdated326",
  "password": "newpassword123"
}

```
Respon Sukses:
```
{
  "error": false,
  "message": "Profile updated successfully"
}
```
Respon Gagal (Name terlalu pendek):
```
{
  "error": true,
  "message": "Name must be at least 12 characters and password at least 8 characters"
}
```

### Show User History
#### Deskripsi : Mendapatkan riwayat pengecekan pengguna saat ini.
- Endpoint: /histories
- Method: GET
- Request Body:
  - Content-Type: application/json
  - Authorization: Bearer <JWT_TOKEN>
- Respon Sukses:
```
{
  "error": false,
  "histories": [
    {
      "id": 1,
      "keluhan": "Gula Darah 200",
      "diagnosa": "Diabetes",
      "tanggal_cek": "2024-12-04 09:45:00"
    },
    {
      "id": 2,
      "keluhan": "Gula Darah 300",
      "diagnosa": "Diabetes",
      "tanggal_cek": "2024-12-03 15:30:00"
    }
  ]
}
```
- Respon Gagal (Token tidak valid/missing):
```
{
  "error": true,
  "message": "Token missing"
}

```


