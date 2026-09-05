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
    if (name == null || name.isBlank()){
      throw new IllegalArgumentException("Arquivo inválido enviado");
    }
    this.name = name;
  }

  /**
   * Adiciona um item ao final da playlist.
   *
   * @param item item a ser adicionado.
   * @return a própria playlist, permitindo encadear chamadas.
   * @throws IllegalArgumentException se o item for nulo, for a própria playlist ou contiver a própria playlist (o que criaria um ciclo).
   */
  public PlaylistNode add(MediaItem item) {
    if (item == null){
      throw new IllegalArgumentException("item não pode ser nulo");
    }
    if (item == this){
      throw new IllegalArgumentException("Não pode adicionar o item dentro dele mesmo");
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
    throw new UnsupportedOperationException("Requisito 1: implemente PlaylistNode.remove");
  }

  /**
   * Lista os filhos diretos da playlist.
   *
   * @return uma lista imutável com os filhos, na ordem de inserção.
   */
  public List<MediaItem> getChildren() {
    throw new UnsupportedOperationException("Exercício 1: implemente PlaylistNode.getChildren");
  }

  /**
   * Verifica se o item está em qualquer nível abaixo desta playlist.
   *
   * @param item item procurado.
   * @return {@code true} se o item for filho direto ou descendente.
   */
  public boolean contains(MediaItem item) {
    throw new UnsupportedOperationException("Requisito 1: implemente PlaylistNode.contains");
  }

  @Override
  public String getName() {
    throw new UnsupportedOperationException("Exercício 1: implemente PlaylistNode.getName");
  }

  @Override
  public int getDurationSeconds() {
    throw new UnsupportedOperationException(
            "Exercício 1: implemente PlaylistNode.getDurationSeconds");
  }

  @Override
  public int getTrackCount() {
    throw new UnsupportedOperationException("Exercício 1: implemente PlaylistNode.getTrackCount");
  }

  @Override
  public List<Track> flatten() {
    throw new UnsupportedOperationException("Exercício 1: implemente PlaylistNode.flatten");
  }
}
