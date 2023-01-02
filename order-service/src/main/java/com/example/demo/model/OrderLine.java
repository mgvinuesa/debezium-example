package com.example.demo.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
public class OrderLine {

	OrderLine() {
	}

	public OrderLine(String item, int quantity, BigDecimal totalPrice) {
		this.item = item;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.status = OrderLineStatus.ENTERED;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_line_ids")
	@SequenceGenerator(name = "order_line_ids", sequenceName = "seq_order_line", allocationSize = 50)
	private Long id;

	private String item;

	private int quantity;

	private BigDecimal totalPrice;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private PurchaseOrder purchaseOrder;

	@Enumerated(EnumType.STRING)
	private OrderLineStatus status;
}
