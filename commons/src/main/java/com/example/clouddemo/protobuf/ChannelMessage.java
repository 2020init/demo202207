// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ChannelMessage.proto

package com.example.clouddemo.protobuf;

public final class ChannelMessage {
  private ChannelMessage() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.example.clouddemo.protobuf.Message)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 type = 1;</code>
     * @return The type.
     */
    int getType();

    /**
     * <code>string fromUid = 2;</code>
     * @return The fromUid.
     */
    String getFromUid();
    /**
     * <code>string fromUid = 2;</code>
     * @return The bytes for fromUid.
     */
    com.google.protobuf.ByteString
        getFromUidBytes();

    /**
     * <code>string toUid = 3;</code>
     * @return The toUid.
     */
    String getToUid();
    /**
     * <code>string toUid = 3;</code>
     * @return The bytes for toUid.
     */
    com.google.protobuf.ByteString
        getToUidBytes();

    /**
     * <code>int64 timestamp = 4;</code>
     * @return The timestamp.
     */
    long getTimestamp();

    /**
     * <code>int64 globalSequence = 5;</code>
     * @return The globalSequence.
     */
    long getGlobalSequence();

    /**
     * <code>int64 sessionSequence = 6;</code>
     * @return The sessionSequence.
     */
    long getSessionSequence();

    /**
     * <code>string body = 7;</code>
     * @return The body.
     */
    String getBody();
    /**
     * <code>string body = 7;</code>
     * @return The bytes for body.
     */
    com.google.protobuf.ByteString
        getBodyBytes();
  }
  /**
   * Protobuf type {@code com.example.clouddemo.protobuf.Message}
   */
  public static final class Message extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.example.clouddemo.protobuf.Message)
      MessageOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Message.newBuilder() to construct.
    private Message(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Message() {
      fromUid_ = "";
      toUid_ = "";
      body_ = "";
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
        UnusedPrivateParameter unused) {
      return new Message();
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Message(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {

              type_ = input.readInt32();
              break;
            }
            case 18: {
              String s = input.readStringRequireUtf8();

              fromUid_ = s;
              break;
            }
            case 26: {
              String s = input.readStringRequireUtf8();

              toUid_ = s;
              break;
            }
            case 32: {

              timestamp_ = input.readInt64();
              break;
            }
            case 40: {

              globalSequence_ = input.readInt64();
              break;
            }
            case 48: {

              sessionSequence_ = input.readInt64();
              break;
            }
            case 58: {
              String s = input.readStringRequireUtf8();

              body_ = s;
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ChannelMessage.internal_static_com_example_clouddemo_protobuf_Message_descriptor;
    }

    @Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ChannelMessage.internal_static_com_example_clouddemo_protobuf_Message_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Message.class, Builder.class);
    }

    public static final int TYPE_FIELD_NUMBER = 1;
    private int type_;
    /**
     * <code>int32 type = 1;</code>
     * @return The type.
     */
    @Override
    public int getType() {
      return type_;
    }

    public static final int FROMUID_FIELD_NUMBER = 2;
    private volatile Object fromUid_;
    /**
     * <code>string fromUid = 2;</code>
     * @return The fromUid.
     */
    @Override
    public String getFromUid() {
      Object ref = fromUid_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        fromUid_ = s;
        return s;
      }
    }
    /**
     * <code>string fromUid = 2;</code>
     * @return The bytes for fromUid.
     */
    @Override
    public com.google.protobuf.ByteString
        getFromUidBytes() {
      Object ref = fromUid_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        fromUid_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TOUID_FIELD_NUMBER = 3;
    private volatile Object toUid_;
    /**
     * <code>string toUid = 3;</code>
     * @return The toUid.
     */
    @Override
    public String getToUid() {
      Object ref = toUid_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        toUid_ = s;
        return s;
      }
    }
    /**
     * <code>string toUid = 3;</code>
     * @return The bytes for toUid.
     */
    @Override
    public com.google.protobuf.ByteString
        getToUidBytes() {
      Object ref = toUid_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        toUid_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TIMESTAMP_FIELD_NUMBER = 4;
    private long timestamp_;
    /**
     * <code>int64 timestamp = 4;</code>
     * @return The timestamp.
     */
    @Override
    public long getTimestamp() {
      return timestamp_;
    }

    public static final int GLOBALSEQUENCE_FIELD_NUMBER = 5;
    private long globalSequence_;
    /**
     * <code>int64 globalSequence = 5;</code>
     * @return The globalSequence.
     */
    @Override
    public long getGlobalSequence() {
      return globalSequence_;
    }

    public static final int SESSIONSEQUENCE_FIELD_NUMBER = 6;
    private long sessionSequence_;
    /**
     * <code>int64 sessionSequence = 6;</code>
     * @return The sessionSequence.
     */
    @Override
    public long getSessionSequence() {
      return sessionSequence_;
    }

    public static final int BODY_FIELD_NUMBER = 7;
    private volatile Object body_;
    /**
     * <code>string body = 7;</code>
     * @return The body.
     */
    @Override
    public String getBody() {
      Object ref = body_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        body_ = s;
        return s;
      }
    }
    /**
     * <code>string body = 7;</code>
     * @return The bytes for body.
     */
    @Override
    public com.google.protobuf.ByteString
        getBodyBytes() {
      Object ref = body_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        body_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (type_ != 0) {
        output.writeInt32(1, type_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(fromUid_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, fromUid_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(toUid_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, toUid_);
      }
      if (timestamp_ != 0L) {
        output.writeInt64(4, timestamp_);
      }
      if (globalSequence_ != 0L) {
        output.writeInt64(5, globalSequence_);
      }
      if (sessionSequence_ != 0L) {
        output.writeInt64(6, sessionSequence_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(body_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 7, body_);
      }
      unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (type_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, type_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(fromUid_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, fromUid_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(toUid_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, toUid_);
      }
      if (timestamp_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(4, timestamp_);
      }
      if (globalSequence_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(5, globalSequence_);
      }
      if (sessionSequence_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(6, sessionSequence_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(body_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, body_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof Message)) {
        return super.equals(obj);
      }
      Message other = (Message) obj;

      if (getType()
          != other.getType()) return false;
      if (!getFromUid()
          .equals(other.getFromUid())) return false;
      if (!getToUid()
          .equals(other.getToUid())) return false;
      if (getTimestamp()
          != other.getTimestamp()) return false;
      if (getGlobalSequence()
          != other.getGlobalSequence()) return false;
      if (getSessionSequence()
          != other.getSessionSequence()) return false;
      if (!getBody()
          .equals(other.getBody())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + TYPE_FIELD_NUMBER;
      hash = (53 * hash) + getType();
      hash = (37 * hash) + FROMUID_FIELD_NUMBER;
      hash = (53 * hash) + getFromUid().hashCode();
      hash = (37 * hash) + TOUID_FIELD_NUMBER;
      hash = (53 * hash) + getToUid().hashCode();
      hash = (37 * hash) + TIMESTAMP_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getTimestamp());
      hash = (37 * hash) + GLOBALSEQUENCE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getGlobalSequence());
      hash = (37 * hash) + SESSIONSEQUENCE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getSessionSequence());
      hash = (37 * hash) + BODY_FIELD_NUMBER;
      hash = (53 * hash) + getBody().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static Message parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Message parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Message parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Message parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Message parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Message parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Message parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Message parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static Message parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static Message parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static Message parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Message parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(Message prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.example.clouddemo.protobuf.Message}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.example.clouddemo.protobuf.Message)
        MessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return ChannelMessage.internal_static_com_example_clouddemo_protobuf_Message_descriptor;
      }

      @Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return ChannelMessage.internal_static_com_example_clouddemo_protobuf_Message_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Message.class, Builder.class);
      }

      // Construct using com.example.clouddemo.protobuf.ChannelMessage.Message.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @Override
      public Builder clear() {
        super.clear();
        type_ = 0;

        fromUid_ = "";

        toUid_ = "";

        timestamp_ = 0L;

        globalSequence_ = 0L;

        sessionSequence_ = 0L;

        body_ = "";

        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return ChannelMessage.internal_static_com_example_clouddemo_protobuf_Message_descriptor;
      }

      @Override
      public Message getDefaultInstanceForType() {
        return Message.getDefaultInstance();
      }

      @Override
      public Message build() {
        Message result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public Message buildPartial() {
        Message result = new Message(this);
        result.type_ = type_;
        result.fromUid_ = fromUid_;
        result.toUid_ = toUid_;
        result.timestamp_ = timestamp_;
        result.globalSequence_ = globalSequence_;
        result.sessionSequence_ = sessionSequence_;
        result.body_ = body_;
        onBuilt();
        return result;
      }

      @Override
      public Builder clone() {
        return super.clone();
      }
      @Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.setField(field, value);
      }
      @Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.addRepeatedField(field, value);
      }
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Message) {
          return mergeFrom((Message)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Message other) {
        if (other == Message.getDefaultInstance()) return this;
        if (other.getType() != 0) {
          setType(other.getType());
        }
        if (!other.getFromUid().isEmpty()) {
          fromUid_ = other.fromUid_;
          onChanged();
        }
        if (!other.getToUid().isEmpty()) {
          toUid_ = other.toUid_;
          onChanged();
        }
        if (other.getTimestamp() != 0L) {
          setTimestamp(other.getTimestamp());
        }
        if (other.getGlobalSequence() != 0L) {
          setGlobalSequence(other.getGlobalSequence());
        }
        if (other.getSessionSequence() != 0L) {
          setSessionSequence(other.getSessionSequence());
        }
        if (!other.getBody().isEmpty()) {
          body_ = other.body_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Message parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Message) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int type_ ;
      /**
       * <code>int32 type = 1;</code>
       * @return The type.
       */
      @Override
      public int getType() {
        return type_;
      }
      /**
       * <code>int32 type = 1;</code>
       * @param value The type to set.
       * @return This builder for chaining.
       */
      public Builder setType(int value) {
        
        type_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 type = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearType() {
        
        type_ = 0;
        onChanged();
        return this;
      }

      private Object fromUid_ = "";
      /**
       * <code>string fromUid = 2;</code>
       * @return The fromUid.
       */
      public String getFromUid() {
        Object ref = fromUid_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          fromUid_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string fromUid = 2;</code>
       * @return The bytes for fromUid.
       */
      public com.google.protobuf.ByteString
          getFromUidBytes() {
        Object ref = fromUid_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          fromUid_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string fromUid = 2;</code>
       * @param value The fromUid to set.
       * @return This builder for chaining.
       */
      public Builder setFromUid(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        fromUid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string fromUid = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearFromUid() {
        
        fromUid_ = getDefaultInstance().getFromUid();
        onChanged();
        return this;
      }
      /**
       * <code>string fromUid = 2;</code>
       * @param value The bytes for fromUid to set.
       * @return This builder for chaining.
       */
      public Builder setFromUidBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        fromUid_ = value;
        onChanged();
        return this;
      }

      private Object toUid_ = "";
      /**
       * <code>string toUid = 3;</code>
       * @return The toUid.
       */
      public String getToUid() {
        Object ref = toUid_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          toUid_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string toUid = 3;</code>
       * @return The bytes for toUid.
       */
      public com.google.protobuf.ByteString
          getToUidBytes() {
        Object ref = toUid_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          toUid_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string toUid = 3;</code>
       * @param value The toUid to set.
       * @return This builder for chaining.
       */
      public Builder setToUid(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        toUid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string toUid = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearToUid() {
        
        toUid_ = getDefaultInstance().getToUid();
        onChanged();
        return this;
      }
      /**
       * <code>string toUid = 3;</code>
       * @param value The bytes for toUid to set.
       * @return This builder for chaining.
       */
      public Builder setToUidBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        toUid_ = value;
        onChanged();
        return this;
      }

      private long timestamp_ ;
      /**
       * <code>int64 timestamp = 4;</code>
       * @return The timestamp.
       */
      @Override
      public long getTimestamp() {
        return timestamp_;
      }
      /**
       * <code>int64 timestamp = 4;</code>
       * @param value The timestamp to set.
       * @return This builder for chaining.
       */
      public Builder setTimestamp(long value) {
        
        timestamp_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 timestamp = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearTimestamp() {
        
        timestamp_ = 0L;
        onChanged();
        return this;
      }

      private long globalSequence_ ;
      /**
       * <code>int64 globalSequence = 5;</code>
       * @return The globalSequence.
       */
      @Override
      public long getGlobalSequence() {
        return globalSequence_;
      }
      /**
       * <code>int64 globalSequence = 5;</code>
       * @param value The globalSequence to set.
       * @return This builder for chaining.
       */
      public Builder setGlobalSequence(long value) {
        
        globalSequence_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 globalSequence = 5;</code>
       * @return This builder for chaining.
       */
      public Builder clearGlobalSequence() {
        
        globalSequence_ = 0L;
        onChanged();
        return this;
      }

      private long sessionSequence_ ;
      /**
       * <code>int64 sessionSequence = 6;</code>
       * @return The sessionSequence.
       */
      @Override
      public long getSessionSequence() {
        return sessionSequence_;
      }
      /**
       * <code>int64 sessionSequence = 6;</code>
       * @param value The sessionSequence to set.
       * @return This builder for chaining.
       */
      public Builder setSessionSequence(long value) {
        
        sessionSequence_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 sessionSequence = 6;</code>
       * @return This builder for chaining.
       */
      public Builder clearSessionSequence() {
        
        sessionSequence_ = 0L;
        onChanged();
        return this;
      }

      private Object body_ = "";
      /**
       * <code>string body = 7;</code>
       * @return The body.
       */
      public String getBody() {
        Object ref = body_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          body_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string body = 7;</code>
       * @return The bytes for body.
       */
      public com.google.protobuf.ByteString
          getBodyBytes() {
        Object ref = body_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          body_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string body = 7;</code>
       * @param value The body to set.
       * @return This builder for chaining.
       */
      public Builder setBody(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        body_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string body = 7;</code>
       * @return This builder for chaining.
       */
      public Builder clearBody() {
        
        body_ = getDefaultInstance().getBody();
        onChanged();
        return this;
      }
      /**
       * <code>string body = 7;</code>
       * @param value The bytes for body to set.
       * @return This builder for chaining.
       */
      public Builder setBodyBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        body_ = value;
        onChanged();
        return this;
      }
      @Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:com.example.clouddemo.protobuf.Message)
    }

    // @@protoc_insertion_point(class_scope:com.example.clouddemo.protobuf.Message)
    private static final Message DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new Message();
    }

    public static Message getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Message>
        PARSER = new com.google.protobuf.AbstractParser<Message>() {
      @Override
      public Message parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Message(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Message> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<Message> getParserForType() {
      return PARSER;
    }

    @Override
    public Message getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_example_clouddemo_protobuf_Message_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_example_clouddemo_protobuf_Message_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\024ChannelMessage.proto\022\036com.example.clou" +
      "ddemo.protobuf\"\211\001\n\007Message\022\014\n\004type\030\001 \001(\005" +
      "\022\017\n\007fromUid\030\002 \001(\t\022\r\n\005toUid\030\003 \001(\t\022\021\n\ttime" +
      "stamp\030\004 \001(\003\022\026\n\016globalSequence\030\005 \001(\003\022\027\n\017s" +
      "essionSequence\030\006 \001(\003\022\014\n\004body\030\007 \001(\tB2\n\036co" +
      "m.example.clouddemo.protobufB\016ChannelMes" +
      "sageP\000b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_example_clouddemo_protobuf_Message_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_example_clouddemo_protobuf_Message_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_example_clouddemo_protobuf_Message_descriptor,
        new String[] { "Type", "FromUid", "ToUid", "Timestamp", "GlobalSequence", "SessionSequence", "Body", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
