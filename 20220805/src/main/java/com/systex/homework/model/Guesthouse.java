package com.systex.homework.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Guesthouse {
    private int id;
    private String name;
    private int rooms;
    private BaseDate startDate;
    private BaseDate endDate;
    private int basePrice;
    private int price;
    private String location;
    private String description;


    public Guesthouse() {
    }

    public Guesthouse(int id, String name, int rooms, BaseDate startDate, BaseDate endDate,
                      int basePrice, int price, String location, String description) {
        this.id = id;
        this.name = name;
        this.rooms = rooms;
        this.startDate = startDate;
        this.endDate = endDate;
        this.basePrice = basePrice;
        this.price = price;
        this.location = location;
        this.description = description;
    }

    public static class GuesthouseBuilder {
        private int id;
        private String name;
        private int rooms;
        private BaseDate startDate;
        private BaseDate endDate;

        private int basePrice;
        private int price;
        private String location;
        private String description;

        public Guesthouse.GuesthouseBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public Guesthouse.GuesthouseBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public Guesthouse.GuesthouseBuilder setRooms(int rooms) {
            this.rooms = rooms;
            return this;
        }

        public Guesthouse.GuesthouseBuilder setBasePrice(int basePrice) {
            this.basePrice = basePrice;
            return this;
        }

        public Guesthouse.GuesthouseBuilder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Guesthouse.GuesthouseBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Guesthouse build() {
            return new Guesthouse(this.id, this.name, this.rooms, this.startDate, this.endDate,
                    this.basePrice, this.price, this.location, this.description);
        }

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRooms() {
        return rooms;
    }

    public BaseDate getStartDate() {
        return startDate;
    }

    public BaseDate getEndDate() {
        return endDate;
    }

    public int getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public void setDate(BaseDate startDate, BaseDate endDate) {
        long startDateEpoch = LocalDate.parse(startDate.toString()).toEpochDay();
        long endDateEpoch = LocalDate.parse(endDate.toString()).toEpochDay();
        if (startDateEpoch > endDateEpoch)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request.");
        this.startDate = startDate;
        this.endDate = endDate;
        this.setPrice();
    }

    public void setPrice() {
        int days = 1;
        String endDateString = this.endDate.toString();
        LocalDate tempLocalDate = LocalDate.parse(this.startDate.toString());
        while (!tempLocalDate.toString().equals(endDateString)) {
            tempLocalDate = tempLocalDate.plusDays(1);
            days++;
        }
        this.price = this.basePrice*days*this.rooms;
    }

    public static Guesthouse createGuesthouseByNameRooms(String name, int rooms) {
        Guesthouse guesthouse = null;

        if (name.equals("凱維精品旅館")) {
            guesthouse = new GuesthouseBuilder()
                    .setId(1+rooms)
                    .setName("凱維精品旅館")
                    .setRooms(rooms)
                    .setBasePrice(1000)
                    .setLocation("士林區, 台北")
                    .setDescription("房型大又舒適")
                    .build();
        }

        if (name.equals("兒童樂園民宿")) {
            guesthouse = new GuesthouseBuilder()
                    .setId(5+rooms)
                    .setName("兒童樂園民宿")
                    .setRooms(rooms)
                    .setBasePrice(800)
                    .setLocation("大安區, 台北")
                    .setDescription("房型舒適離兒童樂園近")
                    .build();
        }

        if (name.equals("這一站幸福")) {
            guesthouse = new GuesthouseBuilder()
                    .setId(10+rooms)
                    .setName("這一站幸福")
                    .setRooms(rooms)
                    .setBasePrice(750)
                    .setLocation("淡水區, 台北")
                    .setDescription("挑高視野佳離海近")
                    .build();
        }

        if (guesthouse == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.");
        return guesthouse;
    }
}
