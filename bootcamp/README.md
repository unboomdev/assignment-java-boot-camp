# Workflow
1. ผู้ใช้เข้าสู่ระบบและทำการ search ด้วยคำว่า “NMD”
2. ระบบเจอข้อมูล “NMD” จำนวน 3 รายการ

| สินค้า                      | ราคา  | ส่วนลด |
|-----------------------------|-------|--------|
| Adidas NMD R1 PK Japan      | 15000 | 14     |
| Adidas NMD R1 Color Black   | 12000 | 33     |
| NMD Sneakers Fashion        | 1900  | 79     |

3. เลือกดูสินค้า "Adidas NMD R1 Color Black" เห็นรายละเอียดต่าง ๆ ของสินค้าชิ้น ดังนี้

| สินค้า                     | ราคา  | ส่วนลด | คำอธิบาย   |
|----------------------------|-------|--------|------------|
| Adidas NMD R1 Color Black  | 12000 | 33     | สวมใส่สบาย |

4. เพิ่มสินค้านี้จำนวน 1 ชิ้น ลงตะกร้า
5. เข้าตะกร้าจะเห็นรายละเอียดสินค้าต่าง ๆ และยอดรวมสุทธิ
6. เลือก ชำระค่าสินค้า ระบบแสดงข้อมูลที่อยู่การจัดส่งและสรุปการสั่งซื้อ
7. เลือก ดำเนินการต่อ เพื่อเลือกรูปแบบการชำระเงิน โดยมีให้เลือกได้แก่ บัตรเครดิตหรือเดบิต, ชำระเงินปลายทาง, ชำระเงินผ่านเคาน์เตอร์
8. เลือก ชำระเงินปลายทาง
9. ทำการสั่งซื้อ ระบบจะแสดงรายละเอียดของใบแจ้งชำระเงิน


# API Design

| name                                                                                                         | path                        | method |
|--------------------------------------------------------------------------------------------------------------|-----------------------------|--------|
| [search_product](https://github.com/unboomdev/assignment-java-boot-camp/wiki/API-Document#search_product)    | /product/search?q={keyword} | GET    |
| [get_product](https://github.com/unboomdev/assignment-java-boot-camp/wiki/API-Document#get_product)          | /product/{productId}        | GET    |
| [add_basket](https://github.com/unboomdev/assignment-java-boot-camp/wiki/API-Document#add_basket)            | /basket                     | POST   |
| [get_basket](https://github.com/unboomdev/assignment-java-boot-camp/wiki/API-Document#get_basket)            | /basket/{userId}            | GET    |
| [get_address](https://github.com/unboomdev/assignment-java-boot-camp/wiki/API-Document#get_address)          | /address/{userId}           | GET    |
| [checkout](https://github.com/unboomdev/assignment-java-boot-camp/wiki/API-Document#checkout)                | /checkout                   | POST   |
| [order_summary](https://github.com/unboomdev/assignment-java-boot-camp/wiki/API-Document#order_summary)      | /order/summary/{orderId}    | GET    |