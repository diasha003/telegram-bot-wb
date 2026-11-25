package org.example.telegrambotwb.entity.subscription;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Table(name = "subscriptions")
@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "chat_Id")
    @NotNull
    private Long chatId;

    @Column(name = "seller_id")
    @NotNull
    private Long sellerId;

    @Setter
    @Getter
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ReportType reportType;

    public Subscription() {
    }

    public Subscription(int id, long chatId, long sellerId, ReportType reportType) {
        this.id = id;
        this.chatId = chatId;
        this.sellerId = sellerId;
        this.reportType = reportType;
    }


    public long getChatId() {
        return chatId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

}
