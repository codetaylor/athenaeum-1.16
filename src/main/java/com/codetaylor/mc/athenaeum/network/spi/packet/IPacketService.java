package com.codetaylor.mc.athenaeum.network.spi.packet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.PacketDistributor;

public interface IPacketService {

  <Q extends IMessage, A extends IMessage> void registerMessage(
      Class<? extends IMessageHandler<Q, A>> messageHandler,
      Class<Q> requestMessageType
  );

  void sendToTrackingChunk(TileEntity tileEntity, IMessage message);

  void sendToDimension(RegistryKey<World> dimension, IMessage message);

  void sendToPlayer(ServerPlayerEntity player, IMessage message);

  void sendToNear(PacketDistributor.TargetPoint targetPoint, IMessage message);

  void sendToTrackingEntity(Entity entity, IMessage message);

  void sendToTrackingEntityAndSelf(Entity entity, IMessage message);

  void sendToTrackingChunk(Chunk chunk, IMessage message);

  void sendToAll(IMessage message);

  void sendToServer(IMessage message);
}