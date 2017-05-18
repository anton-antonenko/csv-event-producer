package com.griddynamics.gridu;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.griddynamics.gridu.Product.Product;

public class RandomEventValuesGenerator {

    public static String getRandomIP() {
        long ip = RandomUtils.nextLong(0, 4294967295L);
        StringBuilder ipAddress = new StringBuilder();
        for (int i = 3; i >= 0; i--) {
            int shift = i * 8;
            ipAddress.append((ip & (0xff << shift)) >> shift);
            if (i > 0) {
                ipAddress.append(".");
            }
        }
        return ipAddress.toString();
    }

    public static String getRandomDate(Long dateFrom, Long dateTo, SimpleDateFormat dateFormat) {
        Date randomDate = new Date(RandomUtils.nextLong(dateFrom, dateTo));
        return dateFormat.format(randomDate);
    }

    public static Pair<String, Product> getRandomProduct() {
        int randCategory = RandomUtils.nextInt(0, CATALOG.keySet().size());
        String category = (String) CATALOG.keySet().toArray()[randCategory];

        List<Product> products = CATALOG.get(category);
        int randProduct = RandomUtils.nextInt(0, products.size());
        Product product = (Product) products.toArray()[randProduct];

        return Pair.of(category, product);
    }

    public static Map<String, List<Product>> CATALOG;
    static {
        CATALOG = new HashMap<>();
        CATALOG.put("Smartphones", Arrays.asList(
                Product("iPhone 5", 399), Product("iPhone 5s", 449), Product("iPhone 6", 499),
                Product("iPhone 6s", 549), Product("iPhone 7", 599), Product("iPhone 7 Plus", 699),
                Product("Samsung Galaxy S6", 399), Product("Samsung Galaxy S7", 499),
                Product("Samsung Galaxy S7 edge", 549), Product("Samsung Galaxy S8", 599),
                Product("Samsung Galaxy S8 Plus", 699), Product("Meizu Pro 6", 399)));

        CATALOG.put("Tablets", Arrays.asList(
                Product("iPad Air", 549), Product("iPad mini", 499), Product("iPad Air 13", 599), Product("iPad Pro", 649),
                Product("Samsung Galaxy Tab S3", 549), Product("Samsung Galaxy Tab S3 mini", 449),
                Product("Asus Zen Pad 3S", 499), Product("Google Pixel C", 599), Product("Microsoft Surface Pro 4", 599),
                Product("Huawei MateBook", 349), Product("Noname Chineese Tablet", 199), Product("Xiaomi Tablet", 249)));

        CATALOG.put("Laptops", Arrays.asList(
                Product("MacBook Air", 799), Product("MakBook", 849), Product("MacBook Pro 13", 1249),
                Product("MacBook Pro 15", 1499), Product("Dell XPS 13", 1349), Product("Dell XPS 15", 1599),
                Product("Asus ZenBook", 1249), Product("Samsung Notebook 7", 1199), Product("Samsung Notebook 5", 1249),
                Product("Samsung Notebook 9", 1399), Product("Microsoft Surface Book", 1599),
                Product("Lenovo ThinkPad 13", 1209), Product("Microsoft Surface Laptop", 1239)));

        CATALOG.put("Cameras", Arrays.asList(
                Product("Fujifilm X-T2", 1209), Product("Canon EOS 5D Mark IV", 1209), Product("Nikon D500", 1209),
                Product("Sony Alpha A7R II", 1209), Product("Nikon D3300", 1209), Product("Fujifilm X100F", 1209),
                Product("Panasonic Lumix ZS100", 1209), Product("Canon EOS Rebel T6i", 1209),
                Product("Nikon D3s", 1209), Product("Nikon D700", 1209), Product("Canon EOS 6D", 1209),
                Product("Leica C", 1209), Product("Leica Q", 1209), Product("Leica X2", 1209)
        ));

        CATALOG.put("Accessories", Arrays.asList(
                Product("Case for iPhone 5", 39), Product("Case for iPhone 5s", 49), Product("Case for iPhone 6", 59),
                Product("Case for iPhone 6s", 59), Product("Case for iPhone 7", 69), Product("Case for iPhone 7 Plus", 79),
                Product("Leather Case for iPhone 5", 59), Product("Leather Case for iPhone 5s", 69),
                Product("Leather Case for iPhone 6", 79), Product("Leather Case for iPhone 6s", 79),
                Product("Leather Case for iPhone 7", 89), Product("Leather Case for iPhone 7 Plus", 99),
                Product("Case for Samsung Galaxy S6", 49), Product("Case for Samsung Galaxy S6 edge", 69),
                Product("Case for Samsung Galaxy S7", 69), Product("Case for Samsung Galaxy S7 edge", 79),
                Product("Case for Samsung Galaxy S8", 89), Product("Case for Samsung Galaxy S8 Plus", 99)
        ));

        CATALOG.put("Smart watches", Arrays.asList(
                Product("iWatch 1 38mm", 499), Product("iWatch 2 38mm", 599), Product("iWatch 1 42mm", 599),
                Product("iWatch 2 42mm", 699), Product("Samsung Gear", 399), Product("Fitbit Blaze", 259),
                Product("Samsung Gear S2", 459), Product("Samsung Gear S3", 599), Product("ASUS ZenWatch 3", 389),
                Product("ASUS ZenWatch", 359), Product("ASUS ZenWatch 2", 459), Product("Huawei Watch", 299)
        ));

        CATALOG.put("Toys", Arrays.asList(
                Product("Ozobot Bit 2.0 Interactive Robot", 359), Product("Sphero Star Wars BB-8", 459),
                Product("EM4 Educational Motorized Robot", 259), Product("Owi 3 in 1 ATR", 389),
                Product("WowWee MiP Robot", 159), Product("WowWee Miposaur Robot", 429), Product("Ollie", 349),
                Product("HEXBUG Nano Robot", 139), Product("Meccanoid G15", 239), Product("Zoomer Cat Robot", 149),
                Product("Hatchimals Draggles", 459), Product("ReCon 6.0", 379)
        ));

        CATALOG.put("Action Cameras", Arrays.asList(
                Product("GoPro Hero 3", 299), Product("GoPro Hero 4", 399), Product("GoPro Hero 5", 499),
                Product("GoPro Silver 3", 199), Product("GoPro Silver 4", 299), Product("GoPro Silver 5", 399),
                Product("GoPro Session 3", 199), Product("GoPro Session 4", 289), Product("GoPro Session 5", 379),
                Product("Xiaomi Yi", 199), Product("Xiaomi Yi 4k", 399), Product("Xiaomi Yi 4k New", 439)
        ));

        CATALOG.put("Monitors", Arrays.asList(
                Product("Samsung 19", 299), Product("Samsung 21", 399), Product("Samsung 24", 499), Product("Samsung 27", 599),
                Product("LG 19", 289), Product("LG 21", 379), Product("LG 24", 539), Product("LG 27", 579),
                Product("Asus 19", 319), Product("Asus 21", 399), Product("Asus 24", 449), Product("Asus 27", 529)

        ));

        CATALOG.put("Routers", Arrays.asList(
                Product("Asus 1", 199), Product("Asus 2", 259), Product("Asus 3", 299), Product("Asus 4", 359),
                Product("TP-Link 1", 149), Product("TP-Link 2", 199), Product("TP-Link 3", 269), Product("TP-Link 4", 299),
                Product("D-Link 1", 189), Product("D-Link 2", 239), Product("D-Link 3", 289), Product("D-Link 4", 369)
        ));

        CATALOG.put("Power Banks", Arrays.asList(
                Product("Xiaomi 5k", 99), Product("Xiaomi 10k", 139), Product("Xiaomi 15k", 199), Product("Xiaomi 20k", 239),
                Product("Zendure 5k", 79), Product("Zendure 10k", 99), Product("Zendure 15k", 149), Product("Zendure 20k", 209),
                Product("Anker PowerCore 5k", 99), Product("Anker PowerCore 10k", 149), Product("Anker PowerCore 15k", 189), Product("Anker PowerCore 20k", 299)
        ));
    }
}
