package chain_quote_indexer;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.70.0)",
    comments = "Source: chain_quote_indexer.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ChainQuoteIndexerGrpc {

  private ChainQuoteIndexerGrpc() {}

  public static final java.lang.String SERVICE_NAME = "chain_quote_indexer.ChainQuoteIndexer";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenResponse> getGetTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetToken",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenResponse> getGetTokenMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenResponse> getGetTokenMethod;
    if ((getGetTokenMethod = ChainQuoteIndexerGrpc.getGetTokenMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getGetTokenMethod = ChainQuoteIndexerGrpc.getGetTokenMethod) == null) {
          ChainQuoteIndexerGrpc.getGetTokenMethod = getGetTokenMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("GetToken"))
              .build();
        }
      }
    }
    return getGetTokenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.Coin> getGetCoinMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCoin",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.Coin.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.Coin> getGetCoinMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.Coin> getGetCoinMethod;
    if ((getGetCoinMethod = ChainQuoteIndexerGrpc.getGetCoinMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getGetCoinMethod = ChainQuoteIndexerGrpc.getGetCoinMethod) == null) {
          ChainQuoteIndexerGrpc.getGetCoinMethod = getGetCoinMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.Coin>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCoin"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.Coin.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("GetCoin"))
              .build();
        }
      }
    }
    return getGetCoinMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.Pool> getGetPoolMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPool",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.Pool.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.Pool> getGetPoolMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.Pool> getGetPoolMethod;
    if ((getGetPoolMethod = ChainQuoteIndexerGrpc.getGetPoolMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getGetPoolMethod = ChainQuoteIndexerGrpc.getGetPoolMethod) == null) {
          ChainQuoteIndexerGrpc.getGetPoolMethod = getGetPoolMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.Pool>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPool"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.Pool.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("GetPool"))
              .build();
        }
      }
    }
    return getGetPoolMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.MapCoin> getFindCoinMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindCoin",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.MapCoin.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.MapCoin> getFindCoinMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.MapCoin> getFindCoinMethod;
    if ((getFindCoinMethod = ChainQuoteIndexerGrpc.getFindCoinMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getFindCoinMethod = ChainQuoteIndexerGrpc.getFindCoinMethod) == null) {
          ChainQuoteIndexerGrpc.getFindCoinMethod = getFindCoinMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.MapCoin>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindCoin"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.MapCoin.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("FindCoin"))
              .build();
        }
      }
    }
    return getFindCoinMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.MapPool> getFindPoolMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindPool",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.MapPool.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.MapPool> getFindPoolMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.MapPool> getFindPoolMethod;
    if ((getFindPoolMethod = ChainQuoteIndexerGrpc.getFindPoolMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getFindPoolMethod = ChainQuoteIndexerGrpc.getFindPoolMethod) == null) {
          ChainQuoteIndexerGrpc.getFindPoolMethod = getFindPoolMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.MapPool>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindPool"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.MapPool.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("FindPool"))
              .build();
        }
      }
    }
    return getFindPoolMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.Quote> getGetQuoteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetQuote",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.Quote.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.Quote> getGetQuoteMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.Quote> getGetQuoteMethod;
    if ((getGetQuoteMethod = ChainQuoteIndexerGrpc.getGetQuoteMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getGetQuoteMethod = ChainQuoteIndexerGrpc.getGetQuoteMethod) == null) {
          ChainQuoteIndexerGrpc.getGetQuoteMethod = getGetQuoteMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.Quote>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetQuote"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.Quote.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("GetQuote"))
              .build();
        }
      }
    }
    return getGetQuoteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.MapQuote> getFindQuotesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindQuotes",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.MapQuote.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.MapQuote> getFindQuotesMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.MapQuote> getFindQuotesMethod;
    if ((getFindQuotesMethod = ChainQuoteIndexerGrpc.getFindQuotesMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getFindQuotesMethod = ChainQuoteIndexerGrpc.getFindQuotesMethod) == null) {
          ChainQuoteIndexerGrpc.getFindQuotesMethod = getFindQuotesMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.MapQuote>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindQuotes"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.MapQuote.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("FindQuotes"))
              .build();
        }
      }
    }
    return getFindQuotesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolders> getListHoldersByTokenAddressMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListHoldersByTokenAddress",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolders.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolders> getListHoldersByTokenAddressMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolders> getListHoldersByTokenAddressMethod;
    if ((getListHoldersByTokenAddressMethod = ChainQuoteIndexerGrpc.getListHoldersByTokenAddressMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getListHoldersByTokenAddressMethod = ChainQuoteIndexerGrpc.getListHoldersByTokenAddressMethod) == null) {
          ChainQuoteIndexerGrpc.getListHoldersByTokenAddressMethod = getListHoldersByTokenAddressMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolders>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListHoldersByTokenAddress"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolders.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("ListHoldersByTokenAddress"))
              .build();
        }
      }
    }
    return getListHoldersByTokenAddressMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolderNumber> getGetHolderCountByTokenAddressMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetHolderCountByTokenAddress",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolderNumber.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolderNumber> getGetHolderCountByTokenAddressMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolderNumber> getGetHolderCountByTokenAddressMethod;
    if ((getGetHolderCountByTokenAddressMethod = ChainQuoteIndexerGrpc.getGetHolderCountByTokenAddressMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getGetHolderCountByTokenAddressMethod = ChainQuoteIndexerGrpc.getGetHolderCountByTokenAddressMethod) == null) {
          ChainQuoteIndexerGrpc.getGetHolderCountByTokenAddressMethod = getGetHolderCountByTokenAddressMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolderNumber>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetHolderCountByTokenAddress"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolderNumber.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("GetHolderCountByTokenAddress"))
              .build();
        }
      }
    }
    return getGetHolderCountByTokenAddressMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.UserTokenRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction> getListUserTransactionsByTokenAddressMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListUserTransactionsByTokenAddress",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.UserTokenRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.UserTokenRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction> getListUserTransactionsByTokenAddressMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.UserTokenRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction> getListUserTransactionsByTokenAddressMethod;
    if ((getListUserTransactionsByTokenAddressMethod = ChainQuoteIndexerGrpc.getListUserTransactionsByTokenAddressMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getListUserTransactionsByTokenAddressMethod = ChainQuoteIndexerGrpc.getListUserTransactionsByTokenAddressMethod) == null) {
          ChainQuoteIndexerGrpc.getListUserTransactionsByTokenAddressMethod = getListUserTransactionsByTokenAddressMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.UserTokenRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListUserTransactionsByTokenAddress"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.UserTokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("ListUserTransactionsByTokenAddress"))
              .build();
        }
      }
    }
    return getListUserTransactionsByTokenAddressMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.PagePool> getQueryPoolsByAddressMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryPoolsByAddress",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.PagePool.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.PagePool> getQueryPoolsByAddressMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.PagePool> getQueryPoolsByAddressMethod;
    if ((getQueryPoolsByAddressMethod = ChainQuoteIndexerGrpc.getQueryPoolsByAddressMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getQueryPoolsByAddressMethod = ChainQuoteIndexerGrpc.getQueryPoolsByAddressMethod) == null) {
          ChainQuoteIndexerGrpc.getQueryPoolsByAddressMethod = getQueryPoolsByAddressMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.PagePool>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryPoolsByAddress"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.PagePool.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("QueryPoolsByAddress"))
              .build();
        }
      }
    }
    return getQueryPoolsByAddressMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLiquidity> getQueryLiquiditiesByAddressMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryLiquiditiesByAddress",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLiquidity.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLiquidity> getQueryLiquiditiesByAddressMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLiquidity> getQueryLiquiditiesByAddressMethod;
    if ((getQueryLiquiditiesByAddressMethod = ChainQuoteIndexerGrpc.getQueryLiquiditiesByAddressMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getQueryLiquiditiesByAddressMethod = ChainQuoteIndexerGrpc.getQueryLiquiditiesByAddressMethod) == null) {
          ChainQuoteIndexerGrpc.getQueryLiquiditiesByAddressMethod = getQueryLiquiditiesByAddressMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLiquidity>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryLiquiditiesByAddress"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLiquidity.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("QueryLiquiditiesByAddress"))
              .build();
        }
      }
    }
    return getQueryLiquiditiesByAddressMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.TxRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction> getQueryTransactionsByAddressMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryTransactionsByAddress",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.TxRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.TxRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction> getQueryTransactionsByAddressMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.TxRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction> getQueryTransactionsByAddressMethod;
    if ((getQueryTransactionsByAddressMethod = ChainQuoteIndexerGrpc.getQueryTransactionsByAddressMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getQueryTransactionsByAddressMethod = ChainQuoteIndexerGrpc.getQueryTransactionsByAddressMethod) == null) {
          ChainQuoteIndexerGrpc.getQueryTransactionsByAddressMethod = getQueryTransactionsByAddressMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.TxRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryTransactionsByAddress"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.TxRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("QueryTransactionsByAddress"))
              .build();
        }
      }
    }
    return getQueryTransactionsByAddressMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.PageFactory> getQueryFactoriesByAddressMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryFactoriesByAddress",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.PageFactory.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.PageFactory> getQueryFactoriesByAddressMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.PageFactory> getQueryFactoriesByAddressMethod;
    if ((getQueryFactoriesByAddressMethod = ChainQuoteIndexerGrpc.getQueryFactoriesByAddressMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getQueryFactoriesByAddressMethod = ChainQuoteIndexerGrpc.getQueryFactoriesByAddressMethod) == null) {
          ChainQuoteIndexerGrpc.getQueryFactoriesByAddressMethod = getQueryFactoriesByAddressMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.PageFactory>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryFactoriesByAddress"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.PageFactory.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("QueryFactoriesByAddress"))
              .build();
        }
      }
    }
    return getQueryFactoriesByAddressMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLine> getQueryKlinesByAddressMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryKlinesByAddress",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLine.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLine> getQueryKlinesByAddressMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLine> getQueryKlinesByAddressMethod;
    if ((getQueryKlinesByAddressMethod = ChainQuoteIndexerGrpc.getQueryKlinesByAddressMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getQueryKlinesByAddressMethod = ChainQuoteIndexerGrpc.getQueryKlinesByAddressMethod) == null) {
          ChainQuoteIndexerGrpc.getQueryKlinesByAddressMethod = getQueryKlinesByAddressMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLine>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryKlinesByAddress"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLine.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("QueryKlinesByAddress"))
              .build();
        }
      }
    }
    return getQueryKlinesByAddressMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.PageRouter> getQueryRoutersByAddressMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryRoutersByAddress",
      requestType = chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest.class,
      responseType = chain_quote_indexer.ChainQuoteIndexerOuterClass.PageRouter.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest,
      chain_quote_indexer.ChainQuoteIndexerOuterClass.PageRouter> getQueryRoutersByAddressMethod() {
    io.grpc.MethodDescriptor<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.PageRouter> getQueryRoutersByAddressMethod;
    if ((getQueryRoutersByAddressMethod = ChainQuoteIndexerGrpc.getQueryRoutersByAddressMethod) == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        if ((getQueryRoutersByAddressMethod = ChainQuoteIndexerGrpc.getQueryRoutersByAddressMethod) == null) {
          ChainQuoteIndexerGrpc.getQueryRoutersByAddressMethod = getQueryRoutersByAddressMethod =
              io.grpc.MethodDescriptor.<chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest, chain_quote_indexer.ChainQuoteIndexerOuterClass.PageRouter>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryRoutersByAddress"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chain_quote_indexer.ChainQuoteIndexerOuterClass.PageRouter.getDefaultInstance()))
              .setSchemaDescriptor(new ChainQuoteIndexerMethodDescriptorSupplier("QueryRoutersByAddress"))
              .build();
        }
      }
    }
    return getQueryRoutersByAddressMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChainQuoteIndexerStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChainQuoteIndexerStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChainQuoteIndexerStub>() {
        @java.lang.Override
        public ChainQuoteIndexerStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChainQuoteIndexerStub(channel, callOptions);
        }
      };
    return ChainQuoteIndexerStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static ChainQuoteIndexerBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChainQuoteIndexerBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChainQuoteIndexerBlockingV2Stub>() {
        @java.lang.Override
        public ChainQuoteIndexerBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChainQuoteIndexerBlockingV2Stub(channel, callOptions);
        }
      };
    return ChainQuoteIndexerBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChainQuoteIndexerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChainQuoteIndexerBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChainQuoteIndexerBlockingStub>() {
        @java.lang.Override
        public ChainQuoteIndexerBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChainQuoteIndexerBlockingStub(channel, callOptions);
        }
      };
    return ChainQuoteIndexerBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChainQuoteIndexerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChainQuoteIndexerFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChainQuoteIndexerFutureStub>() {
        @java.lang.Override
        public ChainQuoteIndexerFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChainQuoteIndexerFutureStub(channel, callOptions);
        }
      };
    return ChainQuoteIndexerFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * 获取Token 信息
     * </pre>
     */
    default void getToken(chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTokenMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取代币信息，含币价(USD)
     * </pre>
     */
    default void getCoin(chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.Coin> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCoinMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取池子信息，含池子大小
     * </pre>
     */
    default void getPool(chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.Pool> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPoolMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取多个代币信息，含币价(USD)
     * </pre>
     */
    default void findCoin(chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.MapCoin> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindCoinMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取多个池子信息，含池子大小
     * </pre>
     */
    default void findPool(chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.MapPool> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindPoolMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取代币行情
     * </pre>
     */
    default void getQuote(chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.Quote> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetQuoteMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取多个代币行情
     * </pre>
     */
    default void findQuotes(chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.MapQuote> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindQuotesMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取token holders列表和 total holder number 按比例倒序 前30的holder
     * </pre>
     */
    default void listHoldersByTokenAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolders> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListHoldersByTokenAddressMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取token holder number
     * </pre>
     */
    default void getHolderCountByTokenAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolderNumber> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetHolderCountByTokenAddressMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取 token address 交易记录（按出块时间倒序）
     * </pre>
     */
    default void listUserTransactionsByTokenAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.UserTokenRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListUserTransactionsByTokenAddressMethod(), responseObserver);
    }

    /**
     * <pre>
     * token address 池子查询（按token所在的池子总量排序）
     * </pre>
     */
    default void queryPoolsByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PagePool> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryPoolsByAddressMethod(), responseObserver);
    }

    /**
     * <pre>
     * token address 流动性变更记录（按出块时间排序）
     * </pre>
     */
    default void queryLiquiditiesByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLiquidity> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryLiquiditiesByAddressMethod(), responseObserver);
    }

    /**
     * <pre>
     * token address 交易变更记录（按出块时间排序）
     * </pre>
     */
    default void queryTransactionsByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.TxRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryTransactionsByAddressMethod(), responseObserver);
    }

    /**
     * <pre>
     * token address AMM(Swap Factory)查询（按成交量排序）
     * </pre>
     */
    default void queryFactoriesByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageFactory> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryFactoriesByAddressMethod(), responseObserver);
    }

    /**
     * <pre>
     * token address k线查询
     * </pre>
     */
    default void queryKlinesByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLine> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryKlinesByAddressMethod(), responseObserver);
    }

    /**
     * <pre>
     * token address 渠道(Swap Router)查询（按成交量排序）
     * </pre>
     */
    default void queryRoutersByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageRouter> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryRoutersByAddressMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ChainQuoteIndexer.
   */
  public static abstract class ChainQuoteIndexerImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ChainQuoteIndexerGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ChainQuoteIndexer.
   */
  public static final class ChainQuoteIndexerStub
      extends io.grpc.stub.AbstractAsyncStub<ChainQuoteIndexerStub> {
    private ChainQuoteIndexerStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChainQuoteIndexerStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChainQuoteIndexerStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取Token 信息
     * </pre>
     */
    public void getToken(chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTokenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取代币信息，含币价(USD)
     * </pre>
     */
    public void getCoin(chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.Coin> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCoinMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取池子信息，含池子大小
     * </pre>
     */
    public void getPool(chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.Pool> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPoolMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取多个代币信息，含币价(USD)
     * </pre>
     */
    public void findCoin(chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.MapCoin> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindCoinMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取多个池子信息，含池子大小
     * </pre>
     */
    public void findPool(chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.MapPool> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindPoolMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取代币行情
     * </pre>
     */
    public void getQuote(chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.Quote> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetQuoteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取多个代币行情
     * </pre>
     */
    public void findQuotes(chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.MapQuote> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindQuotesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取token holders列表和 total holder number 按比例倒序 前30的holder
     * </pre>
     */
    public void listHoldersByTokenAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolders> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListHoldersByTokenAddressMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取token holder number
     * </pre>
     */
    public void getHolderCountByTokenAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolderNumber> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetHolderCountByTokenAddressMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取 token address 交易记录（按出块时间倒序）
     * </pre>
     */
    public void listUserTransactionsByTokenAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.UserTokenRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListUserTransactionsByTokenAddressMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * token address 池子查询（按token所在的池子总量排序）
     * </pre>
     */
    public void queryPoolsByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PagePool> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryPoolsByAddressMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * token address 流动性变更记录（按出块时间排序）
     * </pre>
     */
    public void queryLiquiditiesByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLiquidity> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryLiquiditiesByAddressMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * token address 交易变更记录（按出块时间排序）
     * </pre>
     */
    public void queryTransactionsByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.TxRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryTransactionsByAddressMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * token address AMM(Swap Factory)查询（按成交量排序）
     * </pre>
     */
    public void queryFactoriesByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageFactory> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryFactoriesByAddressMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * token address k线查询
     * </pre>
     */
    public void queryKlinesByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLine> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryKlinesByAddressMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * token address 渠道(Swap Router)查询（按成交量排序）
     * </pre>
     */
    public void queryRoutersByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request,
        io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageRouter> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryRoutersByAddressMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ChainQuoteIndexer.
   */
  public static final class ChainQuoteIndexerBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<ChainQuoteIndexerBlockingV2Stub> {
    private ChainQuoteIndexerBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChainQuoteIndexerBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChainQuoteIndexerBlockingV2Stub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取Token 信息
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenResponse getToken(chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTokenMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取代币信息，含币价(USD)
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.Coin getCoin(chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCoinMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取池子信息，含池子大小
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.Pool getPool(chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPoolMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取多个代币信息，含币价(USD)
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.MapCoin findCoin(chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindCoinMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取多个池子信息，含池子大小
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.MapPool findPool(chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindPoolMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取代币行情
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.Quote getQuote(chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetQuoteMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取多个代币行情
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.MapQuote findQuotes(chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindQuotesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取token holders列表和 total holder number 按比例倒序 前30的holder
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolders listHoldersByTokenAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListHoldersByTokenAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取token holder number
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolderNumber getHolderCountByTokenAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetHolderCountByTokenAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取 token address 交易记录（按出块时间倒序）
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction listUserTransactionsByTokenAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.UserTokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListUserTransactionsByTokenAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * token address 池子查询（按token所在的池子总量排序）
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.PagePool queryPoolsByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryPoolsByAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * token address 流动性变更记录（按出块时间排序）
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLiquidity queryLiquiditiesByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryLiquiditiesByAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * token address 交易变更记录（按出块时间排序）
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction queryTransactionsByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.TxRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryTransactionsByAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * token address AMM(Swap Factory)查询（按成交量排序）
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.PageFactory queryFactoriesByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryFactoriesByAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * token address k线查询
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLine queryKlinesByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryKlinesByAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * token address 渠道(Swap Router)查询（按成交量排序）
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.PageRouter queryRoutersByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryRoutersByAddressMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service ChainQuoteIndexer.
   */
  public static final class ChainQuoteIndexerBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ChainQuoteIndexerBlockingStub> {
    private ChainQuoteIndexerBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChainQuoteIndexerBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChainQuoteIndexerBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取Token 信息
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenResponse getToken(chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTokenMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取代币信息，含币价(USD)
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.Coin getCoin(chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCoinMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取池子信息，含池子大小
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.Pool getPool(chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPoolMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取多个代币信息，含币价(USD)
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.MapCoin findCoin(chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindCoinMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取多个池子信息，含池子大小
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.MapPool findPool(chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindPoolMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取代币行情
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.Quote getQuote(chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetQuoteMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取多个代币行情
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.MapQuote findQuotes(chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindQuotesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取token holders列表和 total holder number 按比例倒序 前30的holder
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolders listHoldersByTokenAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListHoldersByTokenAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取token holder number
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolderNumber getHolderCountByTokenAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetHolderCountByTokenAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取 token address 交易记录（按出块时间倒序）
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction listUserTransactionsByTokenAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.UserTokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListUserTransactionsByTokenAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * token address 池子查询（按token所在的池子总量排序）
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.PagePool queryPoolsByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryPoolsByAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * token address 流动性变更记录（按出块时间排序）
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLiquidity queryLiquiditiesByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryLiquiditiesByAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * token address 交易变更记录（按出块时间排序）
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction queryTransactionsByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.TxRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryTransactionsByAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * token address AMM(Swap Factory)查询（按成交量排序）
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.PageFactory queryFactoriesByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryFactoriesByAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * token address k线查询
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLine queryKlinesByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryKlinesByAddressMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * token address 渠道(Swap Router)查询（按成交量排序）
     * </pre>
     */
    public chain_quote_indexer.ChainQuoteIndexerOuterClass.PageRouter queryRoutersByAddress(chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryRoutersByAddressMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ChainQuoteIndexer.
   */
  public static final class ChainQuoteIndexerFutureStub
      extends io.grpc.stub.AbstractFutureStub<ChainQuoteIndexerFutureStub> {
    private ChainQuoteIndexerFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChainQuoteIndexerFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChainQuoteIndexerFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取Token 信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenResponse> getToken(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTokenMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取代币信息，含币价(USD)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.Coin> getCoin(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCoinMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取池子信息，含池子大小
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.Pool> getPool(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPoolMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取多个代币信息，含币价(USD)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.MapCoin> findCoin(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindCoinMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取多个池子信息，含池子大小
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.MapPool> findPool(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindPoolMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取代币行情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.Quote> getQuote(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetQuoteMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取多个代币行情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.MapQuote> findQuotes(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindQuotesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取token holders列表和 total holder number 按比例倒序 前30的holder
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolders> listHoldersByTokenAddress(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListHoldersByTokenAddressMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取token holder number
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolderNumber> getHolderCountByTokenAddress(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetHolderCountByTokenAddressMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取 token address 交易记录（按出块时间倒序）
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction> listUserTransactionsByTokenAddress(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.UserTokenRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListUserTransactionsByTokenAddressMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * token address 池子查询（按token所在的池子总量排序）
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.PagePool> queryPoolsByAddress(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryPoolsByAddressMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * token address 流动性变更记录（按出块时间排序）
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLiquidity> queryLiquiditiesByAddress(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryLiquiditiesByAddressMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * token address 交易变更记录（按出块时间排序）
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction> queryTransactionsByAddress(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.TxRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryTransactionsByAddressMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * token address AMM(Swap Factory)查询（按成交量排序）
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageFactory> queryFactoriesByAddress(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryFactoriesByAddressMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * token address k线查询
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLine> queryKlinesByAddress(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryKlinesByAddressMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * token address 渠道(Swap Router)查询（按成交量排序）
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageRouter> queryRoutersByAddress(
        chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryRoutersByAddressMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_TOKEN = 0;
  private static final int METHODID_GET_COIN = 1;
  private static final int METHODID_GET_POOL = 2;
  private static final int METHODID_FIND_COIN = 3;
  private static final int METHODID_FIND_POOL = 4;
  private static final int METHODID_GET_QUOTE = 5;
  private static final int METHODID_FIND_QUOTES = 6;
  private static final int METHODID_LIST_HOLDERS_BY_TOKEN_ADDRESS = 7;
  private static final int METHODID_GET_HOLDER_COUNT_BY_TOKEN_ADDRESS = 8;
  private static final int METHODID_LIST_USER_TRANSACTIONS_BY_TOKEN_ADDRESS = 9;
  private static final int METHODID_QUERY_POOLS_BY_ADDRESS = 10;
  private static final int METHODID_QUERY_LIQUIDITIES_BY_ADDRESS = 11;
  private static final int METHODID_QUERY_TRANSACTIONS_BY_ADDRESS = 12;
  private static final int METHODID_QUERY_FACTORIES_BY_ADDRESS = 13;
  private static final int METHODID_QUERY_KLINES_BY_ADDRESS = 14;
  private static final int METHODID_QUERY_ROUTERS_BY_ADDRESS = 15;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_TOKEN:
          serviceImpl.getToken((chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenResponse>) responseObserver);
          break;
        case METHODID_GET_COIN:
          serviceImpl.getCoin((chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.Coin>) responseObserver);
          break;
        case METHODID_GET_POOL:
          serviceImpl.getPool((chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.Pool>) responseObserver);
          break;
        case METHODID_FIND_COIN:
          serviceImpl.findCoin((chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.MapCoin>) responseObserver);
          break;
        case METHODID_FIND_POOL:
          serviceImpl.findPool((chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.MapPool>) responseObserver);
          break;
        case METHODID_GET_QUOTE:
          serviceImpl.getQuote((chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.Quote>) responseObserver);
          break;
        case METHODID_FIND_QUOTES:
          serviceImpl.findQuotes((chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.MapQuote>) responseObserver);
          break;
        case METHODID_LIST_HOLDERS_BY_TOKEN_ADDRESS:
          serviceImpl.listHoldersByTokenAddress((chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolders>) responseObserver);
          break;
        case METHODID_GET_HOLDER_COUNT_BY_TOKEN_ADDRESS:
          serviceImpl.getHolderCountByTokenAddress((chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolderNumber>) responseObserver);
          break;
        case METHODID_LIST_USER_TRANSACTIONS_BY_TOKEN_ADDRESS:
          serviceImpl.listUserTransactionsByTokenAddress((chain_quote_indexer.ChainQuoteIndexerOuterClass.UserTokenRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction>) responseObserver);
          break;
        case METHODID_QUERY_POOLS_BY_ADDRESS:
          serviceImpl.queryPoolsByAddress((chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PagePool>) responseObserver);
          break;
        case METHODID_QUERY_LIQUIDITIES_BY_ADDRESS:
          serviceImpl.queryLiquiditiesByAddress((chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLiquidity>) responseObserver);
          break;
        case METHODID_QUERY_TRANSACTIONS_BY_ADDRESS:
          serviceImpl.queryTransactionsByAddress((chain_quote_indexer.ChainQuoteIndexerOuterClass.TxRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction>) responseObserver);
          break;
        case METHODID_QUERY_FACTORIES_BY_ADDRESS:
          serviceImpl.queryFactoriesByAddress((chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageFactory>) responseObserver);
          break;
        case METHODID_QUERY_KLINES_BY_ADDRESS:
          serviceImpl.queryKlinesByAddress((chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLine>) responseObserver);
          break;
        case METHODID_QUERY_ROUTERS_BY_ADDRESS:
          serviceImpl.queryRoutersByAddress((chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest) request,
              (io.grpc.stub.StreamObserver<chain_quote_indexer.ChainQuoteIndexerOuterClass.PageRouter>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetTokenMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenResponse>(
                service, METHODID_GET_TOKEN)))
        .addMethod(
          getGetCoinMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.Coin>(
                service, METHODID_GET_COIN)))
        .addMethod(
          getGetPoolMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.Pool>(
                service, METHODID_GET_POOL)))
        .addMethod(
          getFindCoinMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.CoinRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.MapCoin>(
                service, METHODID_FIND_COIN)))
        .addMethod(
          getFindPoolMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.PoolRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.MapPool>(
                service, METHODID_FIND_POOL)))
        .addMethod(
          getGetQuoteMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.Quote>(
                service, METHODID_GET_QUOTE)))
        .addMethod(
          getFindQuotesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.MapQuote>(
                service, METHODID_FIND_QUOTES)))
        .addMethod(
          getListHoldersByTokenAddressMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolders>(
                service, METHODID_LIST_HOLDERS_BY_TOKEN_ADDRESS)))
        .addMethod(
          getGetHolderCountByTokenAddressMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.TokenHolderNumber>(
                service, METHODID_GET_HOLDER_COUNT_BY_TOKEN_ADDRESS)))
        .addMethod(
          getListUserTransactionsByTokenAddressMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.UserTokenRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction>(
                service, METHODID_LIST_USER_TRANSACTIONS_BY_TOKEN_ADDRESS)))
        .addMethod(
          getQueryPoolsByAddressMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.PagePool>(
                service, METHODID_QUERY_POOLS_BY_ADDRESS)))
        .addMethod(
          getQueryLiquiditiesByAddressMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLiquidity>(
                service, METHODID_QUERY_LIQUIDITIES_BY_ADDRESS)))
        .addMethod(
          getQueryTransactionsByAddressMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.TxRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.PageTransaction>(
                service, METHODID_QUERY_TRANSACTIONS_BY_ADDRESS)))
        .addMethod(
          getQueryFactoriesByAddressMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.PageFactory>(
                service, METHODID_QUERY_FACTORIES_BY_ADDRESS)))
        .addMethod(
          getQueryKlinesByAddressMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.QuoteRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.PageLine>(
                service, METHODID_QUERY_KLINES_BY_ADDRESS)))
        .addMethod(
          getQueryRoutersByAddressMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chain_quote_indexer.ChainQuoteIndexerOuterClass.AddrRequest,
              chain_quote_indexer.ChainQuoteIndexerOuterClass.PageRouter>(
                service, METHODID_QUERY_ROUTERS_BY_ADDRESS)))
        .build();
  }

  private static abstract class ChainQuoteIndexerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChainQuoteIndexerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return chain_quote_indexer.ChainQuoteIndexerOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ChainQuoteIndexer");
    }
  }

  private static final class ChainQuoteIndexerFileDescriptorSupplier
      extends ChainQuoteIndexerBaseDescriptorSupplier {
    ChainQuoteIndexerFileDescriptorSupplier() {}
  }

  private static final class ChainQuoteIndexerMethodDescriptorSupplier
      extends ChainQuoteIndexerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ChainQuoteIndexerMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ChainQuoteIndexerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChainQuoteIndexerFileDescriptorSupplier())
              .addMethod(getGetTokenMethod())
              .addMethod(getGetCoinMethod())
              .addMethod(getGetPoolMethod())
              .addMethod(getFindCoinMethod())
              .addMethod(getFindPoolMethod())
              .addMethod(getGetQuoteMethod())
              .addMethod(getFindQuotesMethod())
              .addMethod(getListHoldersByTokenAddressMethod())
              .addMethod(getGetHolderCountByTokenAddressMethod())
              .addMethod(getListUserTransactionsByTokenAddressMethod())
              .addMethod(getQueryPoolsByAddressMethod())
              .addMethod(getQueryLiquiditiesByAddressMethod())
              .addMethod(getQueryTransactionsByAddressMethod())
              .addMethod(getQueryFactoriesByAddressMethod())
              .addMethod(getQueryKlinesByAddressMethod())
              .addMethod(getQueryRoutersByAddressMethod())
              .build();
        }
      }
    }
    return result;
  }
}
