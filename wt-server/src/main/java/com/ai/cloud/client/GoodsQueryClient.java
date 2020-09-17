package com.ai.cloud.client;

import com.ai.cloud.client.hystrix.GoodsQueryClientFallbackFactory;
import com.ai.cloud.client.pojo.goods.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@FeignClient(name = "GOODS-QUERY", fallbackFactory = GoodsQueryClientFallbackFactory.class)
public interface GoodsQueryClient {

    @RequestMapping(value = "/qrySalesList/goodsInfo/v1")
    DataResponse<List<Map>> qrySalesGoodsInfo(@RequestBody SalesListReq salesListReq);

    @PostMapping(value = "/querySalesAndAttrVal/init/v1")
    DataResponse<List<Map>> querySalesAndAttrVal(@RequestBody SalesReq salesReq);

    @PostMapping(value = "/queryGoodsByAttrList/init/v1")
    DataResponse<List<GoodsByAttrListRsp>> queryGoodsByAttrList(@RequestBody GoodsAttrReq goodsAttrReq);

    @PostMapping(value = "/queryQQKGoodsName/init/v1")
    DataResponse<List<Map>> queryQQKGoodsName(@RequestBody GoodsQQReq goodsQQReq);

    @PostMapping(value = "/qryProtocolInfo/new/v1")
    DataResponse<List<Map>> qryProtocolInfo(@RequestBody ProtocolInfoReq protocolInfoReq);

    @RequestMapping(value = "/queryGoodsInfo/init/v1")
    DataResponse<GoodsRsp> queryGoodsInfo(@RequestBody String goodsId);


    @RequestMapping(value = "/queryGoodsAttr/init/v1")
    DataResponse<List<GoodsAttrRsp>> queryGoodsAttr(@RequestBody GoodsReq goodsReq);

    @RequestMapping(value = "/qryProByAttr/init/v1")
    DataResponse<List<ProductRsp>> qryProByAttr(@RequestBody ProductReq productReq);

    @RequestMapping(value = "/qryProtocols/old/v1")
    DataResponse<List<Map>>  qryProtocols(@RequestBody ProtocolReq protocolReq);

    @PostMapping(value = "/queryGoodsMessage/init/v1")
    DataResponse<List<Map>> queryGoodsMessage(@RequestBody GoodsMsgReq goodsMsgReq);

    @PostMapping(value = "/queryBroadbandByGoodsid/init/v1")
    DataResponse<List<Map>> queryBroadbandByGoodsid(@RequestBody String goodsId);

    @PostMapping(value = "/queryGoodsPhoto/init/v1")
    DataResponse<List<Map>> queryGoodsPhoto(@RequestBody PhotoReq photoReq);

    @PostMapping(value = "/queryIntroduce/init/v1")
    DataResponse<Map> queryIntroduce(@RequestBody String goodsId);

    @PostMapping(value = "/queryGoodsByProvinceCode/init/v1")
    DataResponse<List<Map>> queryGoodsByProvinceCode(@RequestBody String provinceCode);

    @PostMapping(value = "/queryGoodsByIdAndAttr/init/v1")
    DataResponse<List<Map>> queryGoodsByIdAndAttr(@RequestBody GoodsIdAndAttrReq goodsIdAndAttrReq);

    @PostMapping(value = "/qryStock/v1")
    DataResponse<Map> qryStock(@RequestBody GoodsStockReq goodsStockReq);

}
