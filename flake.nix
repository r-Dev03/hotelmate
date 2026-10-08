# flake.nix
{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  };
  outputs = {nixpkgs, ...}: let
    inherit (nixpkgs) lib;
    withSystem = f:
      lib.foldr lib.recursiveUpdate {}
      (map f ["x86_64-linux" "x86_64-darwin" "aarch64-linux" "aarch64-darwin"]);
  in
    withSystem (
      system: let
        pkgs = nixpkgs.legacyPackages.${system};
      in {
        devShells.${system}.default =
          pkgs.mkShell
          {
            packages = with pkgs; [
              jdt-language-server
              jdk17
              maven
              nodejs_20
            ];
            # Use the project's own Angular CLI (installed by `npm install` in src/main/UI)
            shellHook = ''
              export PATH="$PWD/src/main/UI/node_modules/.bin:$PATH"
            '';
          };
      }
    );
}
