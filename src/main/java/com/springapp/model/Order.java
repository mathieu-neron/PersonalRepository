package com.springapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathieu on 6/21/2016.
 */
public class Order implements Serializable {
    private static final long serialVersionUID = -8288461586793031870L;

    private String editionCode;
    private String addonOfferingCode;
    private String pricingDuration;
    private List<OrderItem> items = new ArrayList<>();
}
