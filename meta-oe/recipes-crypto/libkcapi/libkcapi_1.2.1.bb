SUMMARY = "Linux Kernel Crypto API User Space Interface Library"
HOMEPAGE = "http://www.chronox.de/libkcapi.html"
LICENSE = "BSD-3-Clause | GPL-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=c78be93ed8d1637f2a3f4a83ff9d5f54"

S = "${WORKDIR}/git"
SRCREV = "d41284525ec8960e9a828979cfe269012b7df8db"
SRC_URI = "git://github.com/smuellerDD/libkcapi.git;branch=master;protocol=https \
           file://0001-Disable-use-of-__NR_io_getevents-when-not-defined.patch \
           "

inherit autotools

PACKAGECONFIG ??= ""
PACKAGECONFIG[testapp] = "--enable-kcapi-test,,,bash"
PACKAGECONFIG[apps] = "--enable-kcapi-speed --enable-kcapi-hasher --enable-kcapi-rngapp --enable-kcapi-encapp --enable-kcapi-dgstapp,,,"
PACKAGECONFIG[hasher_only] = "--enable-kcapi-hasher --disable-lib-kdf --disable-lib-sym --disable-lib-aead --disable-lib-rng,,,"

do_install:append() {
    # bindir contains testapp and apps.  However it is always created, even
    # when no binaries are installed (empty bin_PROGRAMS in Makefile.am),
    rmdir --ignore-fail-on-non-empty ${D}${bindir}

    # Remove the generated binary checksum files
    rm -f ${D}${bindir}/.*.hmac
    rm -f ${D}${libdir}/.*.hmac
}

CPPFLAGS:append:libc-musl:toolchain-clang = " -Wno-error=sign-compare"

BBCLASSEXTEND = "native"
