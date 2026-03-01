export interface ProductRequest {
  name: string;
  categoryId: number;
  unitId: number;
  purchasePrice: number;
  sellingPrice: number;
  gstPercent: number;
  skuCode: string;
  retailerId: number;
}

export interface ProductCategoryOption {
  id: number;
  name: string;
}

export interface UnitOption {
  id: number;
  name: string;
}
