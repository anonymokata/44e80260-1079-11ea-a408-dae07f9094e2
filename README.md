Checkout Order Total Kata
======
Description 
-------------------------------
This project, a grocery point-of-sale system, was build with Java program language. It is a show case that implements bushiness logic to calculate grocery checkout pre-tax total price by scanning or entering items in terms of weight and quantity. 

Usage
-------------
Scanning or entering an item with its quantity or weight, the program calculates total pre-tax price of all items.
 
1. Supports calculate price with items that has no any special offer. 
2. Supports a special offer of a markdown: when an item reduce its price per unit, the total price will be reduce as per unit when checkout.
3. Supports a special offer of a 'Buy N get M for free', such as "Buy 2 get 1 for free" or "Buy 2 pounds get 1 pound for free
4. Supports a special offer of a 'Buy N get M for X% off', such as "Buy 1 get 1 half off" or "Buy 1 pound get 1 pound 25% off".
5. Supports a special offer of package deal, such as "Buy 3 for $5" or "Buy 3 pounds for $5".
6. Supports a limitation when items has special offers, such as buy 2 get 2 for free, limit 8.
7. Supports remove items after scanned items. It supports remove all or partial quantity remove for items that is not just single one.

Technologies used
--
Java 11.0.2       
Junit 4
Intellij

Test Cases
--
1. Scan a single items, it returns current checkout total price.
2. Scan multiple items without any special, it returns correct checkout total price.
3. Scan items contain markdown items(in quantity and in weight), it returns correct checkout total price.
4. Scan items contain markdown items and package deal items(in quantity and in weight), it returns correct checkout total price.
5. Scan items contain markdown items, package deal items, and buy N get M for free(in quantity and in weight), it returns correct checkout total price.
6. Scan items contain markdown items, package deal items, and but N get M for X% off with or without limitation(in quantity and in weight), it returns correct checkout total price.
7. Scan all items and remove one or more items(including partial removal in quantity, not in weight), returns updated checkout total price
 
Simple UML Class Diagram For This Project
---
![UML Diagram - Checkout Order Total Kata](https://user-images.githubusercontent.com/43623996/68981671-37dd5480-07d2-11ea-9833-5aed8f8fb531.png))
Note
---
1. Item that calculates by its weight has to be entered as a double type when scanning it.
2. The total price is pre-tax price.
3. Regarding to the final requirements of this project : Support "Buy N, get M of equal or lesser value for %X off" on weighted items, I interpreted it as consumers can buy a specific item at at its original price, you get that same item at a certain percentage off. Just as the listed example: Buy 2 pounds of ground beef, get 1 pound half off". 

