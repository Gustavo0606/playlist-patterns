package com.playlist.composite;

import com.playlist.core.Track;
import java.util.ArrayList;
import java.util.List;

/**
 * Composite do padrão Composite: uma playlist que pode conter faixas e outras playlists.
 */
public class PlaylistNode implements MediaItem {
  private final String name;
  private List<MediaItem> itens = new ArrayList<>();

  /**
   * Cria uma playlist vazia.
   *
   * @param name nome da playlist. Não pode ser nulo nem em branco.
   * @throws IllegalArgumentException se o nome for nulo ou em branco.
   */
  public PlaylistNode(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Arquivo inválido enviado");
    }
    this.name = name;
  }

  /**
   * Adiciona um item ao final da playlist.
   *
   * @param item item a ser adicionado.
   * @return a própria playlist, permitindo encadear chamadas.
   * @throws IllegalArgumentException se o item for nulo, for a própria playlist
   *     ou contiver a própria playlist (o que criaria um ciclo).
   */
  public PlaylistNode add(MediaItem item) {
    if (item == null) {
      throw new IllegalArgumentException("item não pode ser nulo");
    }
    if (item == this) {
      throw new IllegalArgumentException("Não pode adicionar o item dentro dele mesmo");
    }
    if (item instanceof PlaylistNode && ((PlaylistNode) item).contains(this)) {
      throw new IllegalArgumentException("Adicionar o item criaria um ciclo");
    }
    itens.add(item);
    return this;
  }

  /**
   * Remove um filho direto da playlist.
   *
   * @param item item a ser removido.
   * @return {@code true} se o item era filho direto e foi removido.
   */
  public boolean remove(MediaItem item) {
    return itens.remove(item);
  }

  /**
   * Lista os filhos diretos da playlist.
   *
   * @return uma lista imutável com os filhos, na ordem de inserção.
   */
  public List<MediaItem> getChildren() {
    return List.copyOf(itens);
  }

  /**
   * Verifica se o item está em qualquer nível abaixo desta playlist.
   *
   * @param item item procurado.
   * @return {@code true} se o item for filho direto ou descendente.
   */
  public boolean contains(MediaItem item) {
    for (MediaItem i : itens) {
      if (i == item) {
        return true;
      }
      if (i instanceof PlaylistNode node) {
        if (node.contains(item)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getDurationSeconds() {
    int acumulador = 0;
    for (MediaItem i : itens) {
      acumulador += i.getDurationSeconds();
    }
    return acumulador;
  }

  @Override
  public int getTrackCount() {
    int acumulador = 0;
    for (MediaItem i : itens) {
      acumulador += i.getTrackCount();
    }
    return acumulador;
  }

  @Override
  public List<Track> flatten() {
    List<Track> listaGeral = new ArrayList<>();
    for (MediaItem i : itens) {
      listaGeral.addAll(i.flatten());
    }
    return listaGeral;
  }
}