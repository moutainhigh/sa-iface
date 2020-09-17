package com.ai.cloud.client.hystrix;

import com.ai.cloud.client.GoodsQueryClient;
import com.ai.cloud.client.pojo.goods.*;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Component
public class GoodsQueryClientFallbackFactory implements FallbackFactory<GoodsQueryClient> {

    private static Logger logger = LoggerFactory.getLogger(GoodsQueryClientFallbackFactory.class);


    @Override
    public GoodsQueryClient create(Throwable throwable) {
        logger.error("goods query service overload or timeout:{}", throwable.getMessage());
        return new GoodsQueryClient() {
            @Override
            public DataResponse<List<Map>> qrySalesGoodsInfo(@RequestBody SalesListReq salesListReq) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }

            @Override
            public DataResponse<List<Map>> querySalesAndAttrVal(@RequestBody SalesReq salesReq) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }

            @Override
            public DataResponse<List<GoodsByAttrListRsp>> queryGoodsByAttrList(GoodsAttrReq goodsAttrReq) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }

            @Override
            public DataResponse<List<Map>> queryQQKGoodsName(GoodsQQReq goodsQQReq) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }

            @Override
            public DataResponse<List<Map>> qryProtocolInfo(ProtocolInfoReq protocolInfoReq) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }

            @Override
            public DataResponse<GoodsRsp> queryGoodsInfo(String goodsId) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }

            @Override
            public DataResponse<List<GoodsAttrRsp>> queryGoodsAttr(GoodsReq goodsReq) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }

            @Override
            public DataResponse<List<ProductRsp>> qryProByAttr(ProductReq productReq) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }

            @Override
            public DataResponse<List<Map>> qryProtocols(ProtocolReq protocolReq) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }

            @Override
            public DataResponse<Map> queryIntroduce(String goodsId) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }

            @Override
            public DataResponse<List<Map>> queryGoodsByProvinceCode(String provinceCode) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }

            @Override
            public DataResponse<List<Map>> queryGoodsByIdAndAttr(GoodsIdAndAttrReq goodsIdAndAttrReq) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }

            @Override
            public DataResponse<Map> qryStock(GoodsStockReq goodsStockReq) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }

            @Override
            public DataResponse<List<Map>> queryGoodsMessage(GoodsMsgReq goodsMsgReq) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }

            @Override
            public DataResponse<List<Map>> queryBroadbandByGoodsid(String goodsId) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }

            @Override
            public DataResponse<List<Map>> queryGoodsPhoto(PhotoReq photoReq) {
                DataResponse dataResponse = new DataResponse();
                dataResponse.setCode("9999");
                dataResponse.setMsg("goods query service overload or timeout");
                return dataResponse;
            }
        };

    }
}
