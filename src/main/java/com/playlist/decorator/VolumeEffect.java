package com.playlist.decorator;

/**
 * Efeito que multiplica o volume das amostras, com corte em {@code [-1.0, 1.0]}.
 */
public final class VolumeEffect extends AudioEffect {

  private final double factor;

  /**
   * Cria o efeito de volume.
   *
   * @param wrapped áudio decorado.
   * @param factor fator multiplicador do volume.
   */
  public VolumeEffect(AudioTrack wrapped, double factor) {
    super(wrapped);
    this.factor = factor;
  }

  @Override
  protected String describe() {
    return "volume(" + factor + ")";
  }

  @Override
  public double[] getSamples() {
    double[] samples = wrapped.getSamples();
    for (int i = 0; i < samples.length; i++) {
      double novoValor = samples[i] * factor;
      samples[i] = Math.max(-1.0, Math.min(1.0, novoValor));
    }
    return samples;
  }
}