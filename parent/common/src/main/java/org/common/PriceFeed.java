package org.common;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

import java.io.Serializable;

@SpaceClass
public class PriceFeed implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
    private String symbol;
    private Float tradePrice;
    private Float bidPrice;
    private Float askPrice;
    private Integer bidSize;
    private Integer askSize;

    @SpaceId(autoGenerate = true)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
    @SpaceRouting
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Float getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(Float tradePrice) {
		this.tradePrice = tradePrice;
	}
	
	public Float getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(Float bidPrice) {
		this.bidPrice = bidPrice;
	}
	public Float getAskPrice() {
		return askPrice;
	}
	public void setAskPrice(Float askPrice) {
		this.askPrice = askPrice;
	}
	public Integer getBidSize() {
		return bidSize;
	}
	public void setBidSize(Integer bidSize) {
		this.bidSize = bidSize;
	}
	public Integer getAskSize() {
		return askSize;
	}
	public void setAskSize(Integer askSize) {
		this.askSize = askSize;
	}
	
	@Override
	public String toString() {
		return "PriceFeed [id=" + id + ", symbol=" + symbol + ", price=" + tradePrice + ", bidPrice=" + bidPrice
				+ ", askPrice=" + askPrice + ", bidSize=" + bidSize + ", askSize=" + askSize + "]";
	}
}