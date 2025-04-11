package com.ippon.vluce.domain.usecases.exceptions

import com.ippon.vluce.domain.model.Player

sealed class PlayerException(message: String): Exception(message) {

    class NoSuchPseudoException(val pseudo: String):
        PlayerException("Pas de joueur trouvé avec le pseudo \"$pseudo\"")

    class PseudoAlreadyExistsException(val pseudo: String):
        PlayerException("Le pseudo \"$pseudo\" est déjà utilisé par un joueur. Veuillez en choisir un autre.")

}