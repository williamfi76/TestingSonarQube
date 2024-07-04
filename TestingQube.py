from typing import List, Dict, Optional, Union
from collections import deque

class Identity:
    def __init__(self, username: str, password: Optional[str]):
        self.username = username
        self.password = password

class Membership:
    def __init__(self, identity: Identity, noTelp: str):
        self.identity = identity
        self.noTelp = noTelp

class FoodAndBeverage:
    def __init__(self, identity: Optional[Identity], namaFnB: str, hargaFnB: float):
        self.identity = identity
        self.namaFnB = namaFnB
        self.hargaFnB = hargaFnB

class Pelanggan:
    def __init__(self, identity: Identity):
        self.identity = identity

class Reservasi:
    def __init__(self, banyakOrang: int, tanggal: int, bulan: int, tahun: str):
        self.banyakOrang = banyakOrang
        self.tanggal = tanggal
        self.bulan = bulan
        self.tahun = tahun

# Initialize variables and data
menu: List[FoodAndBeverage] = [
    FoodAndBeverage(None, "Tidak memilih menu", 0),
    FoodAndBeverage(None, "Ricebowl Ayam Geprek Sambal Special", 20000),
    FoodAndBeverage(None, "Ricebowl Chicken Sambal Matah", 24000),
    FoodAndBeverage(None, "Ricebowl Beef Teriyaki", 22000),
    FoodAndBeverage(None, "Dimsum Ayam", 16000),
    FoodAndBeverage(None, "Dimsum Udang", 16000),
    FoodAndBeverage(None, "Dimsum Smoke Beef", 16000),
    FoodAndBeverage(None, "Kopi Susu Coco Latte", 19000),
    FoodAndBeverage(None, "Kopi Susu Pandan", 19000),
    FoodAndBeverage(None, "Kopi susu Hazelnut", 19000),
    FoodAndBeverage(None, "Choco Classic", 17000),
    FoodAndBeverage(None, "Red Velvet Latte", 17000),
    FoodAndBeverage(None, "Lemon Fresh Cup", 10000),
    FoodAndBeverage(None, "Ice Cream Cone", 5500),
    FoodAndBeverage(None, "Croffle", 15000),
    FoodAndBeverage(None, "Cireng Bumbu Rujak", 15000),
    FoodAndBeverage(None, "Sosis Bakar", 16500),
    FoodAndBeverage(None, "French Fries", 12000),
    FoodAndBeverage(None, "Crepes", 4000)
]

membership: Dict[str, Membership] = {
    "001": Membership(Identity("grace", "hai123"), "081266781312"),
    "002": Membership(Identity("deaa", "iya123"), "081213176564"),
    "003": Membership(Identity("depott", "winter123"), "082354678321"),
    "004": Membership(Identity("tantan", "mobil123"), "083289657834"),
    "005": Membership(Identity("barbie", "cantik123"), "083487695432")
}

nomorAntrian = 1
uniqueItems: List[str] = []
queueFnB = deque()
tampungMenu: List[FoodAndBeverage] = []
tampungPelanggan: List[FoodAndBeverage] = []
daftarReservasi: List[Reservasi] = []
kapasitasReservasi = 50
banyakOrang = 0
totalBayarSemuanya = 0.0
delivery = 0
pilihan = 0
getDiskon = 0.0

def rolePelanggan():
    global getDiskon, pilihan
    while True:
        jawaban = int(input("Apakah kamu merupakan pelanggan \n1. Non-membership\n2. Membership\n"))
        if jawaban == 1:
            print("-----------------")
            namaPelanggan = input("Silakan masukkan nama Anda: ")
            tampungPelanggan.append(FoodAndBeverage(Identity(namaPelanggan, None), None, 0.0))
            getDiskon = 0
            if int(input("apakah untuk reservasi event atau bukan? (1 for ya, 2 for no)\n")) == 1:
                reservationEvent(namaPelanggan)
            else:
                purchaseMeal(namaPelanggan)
            break
        elif jawaban == 2:
            print("-----------------")
            username = input("Masukkan username Anda: ")
            password = input("Masukkan password Anda: ")
            for key, value in membership.items():
                if value.identity.username == username and value.identity.password == password:
                    print("Anda termasuk dalam Membership.")
                    tampungPelanggan.append(FoodAndBeverage(Identity(username, password), None, 0.0))
                    getDiskon += 0.1
                    if int(input("apakah untuk reservasi event atau bukan? (1 for ya, 2 for no)\n")) == 1:
                        reservationEvent(username)
                    else:
                        purchaseMeal(username)
                    return
            print(f"Username {username} dengan password {password} tidak ada dalam List Membership.")
        else:
            print("Tidak ada pilihan menu tersebut.")

def purchaseMeal(namaPelanggan: str):
    global totalBayarSemuanya, delivery
    while True:
        print("-- SILAHKAN PILIH HIDANGAN --")
        print("-- MENU --")
        for i, item in enumerate(menu):
            print(f"{i}. {item.namaFnB}\t\tRp. {item.hargaFnB}")
        pilihmenu = int(input("Boleh kaka, mau pilih menu no berapa?\n"))
        if pilihmenu != 0 and pilihmenu < len(menu):
            banyakmenu = int(input("Yaudah boleh, mau berapa banyak?\n"))
            for _ in range(banyakmenu):
                tampungMenu.append(FoodAndBeverage(Identity(namaPelanggan, None), menu[pilihmenu].namaFnB, menu[pilihmenu].hargaFnB))
            print("Menu berhasil di tambahkan ke pesanan")
        else:
            print(menu[0].namaFnB if pilihmenu == 0 else "Maaf, menu yang anda pilih tidak tersedia :)")
            break
        printPesananPelanggan(namaPelanggan)
        if not bool(input("Ingin memesan lagi? (true/false)\n")):
            delivery = int(input("Metode pengantaran \n1. Take away \n2. Dine in?\n"))
            totalBayar = transaction(namaPelanggan, banyakmenu)
            paymentMethod(namaPelanggan, totalBayar, getDiskon)
            break

def transaction(namaPelanggan: str, qty: int) -> float:
    global totalBayarSemuanya, delivery
    total = sum(item.hargaFnB for item in tampungMenu if item.identity.username == namaPelanggan)
    if delivery == 1:
        total += 1000 * qty
    totalBayarSemuanya += total - (total * getDiskon)
    return total

def printPesananPelanggan(namaPelanggan: str):
    print(f"Nama Pelanggan: {namaPelanggan}")
    print("--> menu yang Dipesan <--")
    uniqueItems.clear()
    uniqueItems.extend({item.namaFnB for item in tampungMenu if item.identity.username == namaPelanggan})
    for item in uniqueItems:
        count = sum(1 for a in tampungMenu if a.namaFnB == item and a.identity.username == namaPelanggan)
        harga = sum(a.hargaFnB for a in tampungMenu if a.namaFnB == item and a.identity.username == namaPelanggan)
        print(f"{item} ({count})\t total = {harga}")

def paymentMethod(namaPelanggan: str, totalBayar: float, diskon: float):
    while True:
        pembayaran = int(input(
            "Mau bayar pake apa? \n1. OVO \n2. Qris \n3. GoPay \n4. Dana \n5. Isaku \n6. Exit\n"))
        if pembayaran in range(1, 6):
            printPesananPelanggan(namaPelanggan)
            print("------------")
            print(f"Total pembayaran sebelum diskon:\tRp. {totalBayar}")
            totalBayar -= totalBayar * diskon
            print(f"Diskon 10%:\t\t\tRp. {totalBayar * diskon}")
            print(f"Total pembayaran setelah diskon:\tRp. {totalBayar}")
            print("------------")
            queueFnB.append(namaPelanggan)
            print(f"Nama pelanggan : {namaPelanggan}")
            print(f"Nomor antrian kamu : {len(queueFnB)}")
            break
        else:
            print("Pembayaran Anda gagal!")

def getTotalOrangReserved(tanggal: int, bulan: int, tahun: str) -> int:
    return sum(reservasi.banyakOrang for reservasi in daftarReservasi if reservasi.tanggal == tanggal and reservasi.bulan == bulan and reservasi.tahun == tahun)

def reservationEvent(namaP
