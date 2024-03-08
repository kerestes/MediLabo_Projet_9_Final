export class Adresse {
  private _id?:number

  constructor(
    public adresse?:string,
    public ville?:string,
    public codePostal?:string,
    id?:number){
    this._id = id;
  }

  get id(){
    return this._id;
  }
}
