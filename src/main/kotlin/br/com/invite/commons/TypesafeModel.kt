package br.com.invite.commons

interface TypesafeModel<T> {
    abstract fun getDefaultValue(): T
}